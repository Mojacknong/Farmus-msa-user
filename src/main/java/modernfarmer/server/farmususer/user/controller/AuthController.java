package modernfarmer.server.farmususer.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.dto.response.TokenResponseDto;
import modernfarmer.server.farmususer.user.service.AuthService;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/kakao-login")
    public BaseResponseDto<TokenResponseDto> kakaoLogin(HttpServletRequest request)  {

        String accessToken = jwtTokenProvider.resolveToken(request);

        return authService.kakaoLogin(accessToken);
    }
  

    @PostMapping(value = "/google-login")
    public BaseResponseDto<TokenResponseDto> googleLogin(HttpServletRequest request)  {

        String accessToken = jwtTokenProvider.resolveToken(request);

        return authService.googleLogin(accessToken);
    }


}