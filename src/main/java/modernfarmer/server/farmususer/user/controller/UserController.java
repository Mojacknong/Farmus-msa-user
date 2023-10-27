package modernfarmer.server.farmususer.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.user.dto.response.ResponseDto;
import modernfarmer.server.farmususer.user.dto.response.TokenResponseDto;

import modernfarmer.server.farmususer.user.service.AuthService;
import modernfarmer.server.farmususer.user.service.UserService;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @DeleteMapping("/logout")
    public ResponseDto logout(HttpServletRequest request)  {


        String userId = jwtTokenProvider.getUserId(request);

        ResponseDto logoutResponseDto = userService.logout(Long.valueOf(userId));

        log.info("로그아웃 완료");

        return logoutResponseDto;
    }

    @GetMapping(value = "/reissue-token")
    public TokenResponseDto reissueToken(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);
        String refreshToken = jwtTokenProvider.resolveToken(request);

        TokenResponseDto reissueTokenResponseDto = userService.reissueToken(refreshToken, Long.valueOf(userId));

        log.info("토큰 재발급 완료");

        return reissueTokenResponseDto;
    }
}
