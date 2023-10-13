package modernfarmer.server.farmususer.user.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);


    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${token.access}")
    private long accessTokenTime; // 1달 토큰 유효

    @Value("${token.refresh}")
    private long refreshTokenTime; // 1달 토큰 유효

    @PostConstruct
    protected void init() {
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작", StandardCharsets.UTF_8);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createAccessToken(Long userId, String roles) {            // 토큰 생성

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public String createRereshToken() {            // 토큰 생성

        Date now = new Date();
        String token = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");

        String tokenHeader = request.getHeader("access");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        } else {
            // 예외 처리: 헤더가 없거나 "Bearer " 접두사가 없는 경우
            throw new IllegalArgumentException("Invalid access token header");
        }
    }



    public boolean validateRefreshToken(String token) {                         // 토큰 유효성 확인
        LOGGER.info("[validateRefreshToken] 토큰 유효 체크 시작");
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        if (!claims.getBody().isEmpty()) {
            LOGGER.info("[validateRefreshToken] 토큰 유효 체크 완료");
            return true;
        }
        return false;
    }
}
