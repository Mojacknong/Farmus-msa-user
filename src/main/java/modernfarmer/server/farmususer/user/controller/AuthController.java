package modernfarmer.server.farmususer.user.controller;

import modernfarmer.server.farmususer.user.dto.response.ResponseDto;
import modernfarmer.server.farmususer.user.dto.response.TokenResponseDto;
import modernfarmer.server.farmususer.user.service.AuthService;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/user/auth")
public class AuthController {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider){
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "/kakao-login")
    public TokenResponseDto kakaoLogin(HttpServletRequest request)  {

        String accessToken = jwtTokenProvider.resolveToken(request);

        TokenResponseDto reissueTokenResponseDto = authService.kakaoLogin(accessToken);

        LOGGER.info("카카오 로그인 완료");

        return reissueTokenResponseDto;
    }

    @DeleteMapping("/logout")
    public ResponseDto logout(HttpServletRequest request)  {


        String userId = jwtTokenProvider.getUserId(request);

        ResponseDto logoutResponseDto = authService.logout(Long.valueOf(userId));

        LOGGER.info("로그아웃 완료");

        return logoutResponseDto;
    }

    @GetMapping(value = "/reissue-token")
    public TokenResponseDto reissueToken(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);
        String refreshToken = jwtTokenProvider.resolveToken(request);

        TokenResponseDto reissueTokenResponseDto = authService.reissueToken(refreshToken, Long.valueOf(userId));

        LOGGER.info("토큰 재발급 완료");

        return reissueTokenResponseDto;
    }








}