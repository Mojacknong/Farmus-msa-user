package modernfarmer.server.farmususer.user.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


@Slf4j
@Component
public class JwtTokenProvider {



    @Value("${jwt.secret}")
    private String secretKey;


    private final long accessTokenTime = 365L * 24 * 60 * 60 * 1000; // 1달 토큰 유효


    private final long refreshTokenTime = 30L * 1000 * 2 * 1000; // 1달 토큰 유효

    @PostConstruct
    protected void init() {
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createAccessToken(Long userId, String roles) {            // 토큰 생성

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public String createRefreshToken(Long userId) {            // 토큰 생성
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public String resolveToken(HttpServletRequest request) {

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        } else {
            // 예외 처리: 헤더가 없거나 "Bearer " 접두사가 없는 경우
            throw new IllegalArgumentException("Invalid access token header");
        }
    }

    public String getFirebaseToken(HttpServletRequest request) {

        return request.getHeader("FirebaseToken");

    }

    public String getUserId(HttpServletRequest request) {

        return request.getHeader("user");

    }



}
