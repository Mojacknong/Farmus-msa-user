package modernfarmer.server.farmususer.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.user.dto.request.ProduceNicknameRequest;
import modernfarmer.server.farmususer.user.dto.response.ProfileImageResponseDto;
import modernfarmer.server.farmususer.user.dto.response.ResponseDto;
import modernfarmer.server.farmususer.user.dto.response.TokenResponseDto;

import modernfarmer.server.farmususer.user.service.UserService;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/profileImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto produceProfileImage(HttpServletRequest request, @RequestPart("file") MultipartFile multipartFile) throws IOException {

        String userId = jwtTokenProvider.getUserId(request);
        log.info(userId);
        ResponseDto responseDto = userService.emitProfileImage(Long.valueOf(userId), multipartFile);

        return responseDto;

    }

    @GetMapping(value = "/profileImage")
    public ProfileImageResponseDto selectProfileImage(HttpServletRequest request){

        String userId = jwtTokenProvider.getUserId(request);


        ProfileImageResponseDto profileImageResponseDto = userService.selectProfileImage(Long.valueOf(userId));

        return profileImageResponseDto;
    }


    @DeleteMapping(value = "/delete")
    public ResponseDto deleteUser(HttpServletRequest request){

        String userId = jwtTokenProvider.getUserId(request);

        ResponseDto responseDto = userService.deleteUser(Long.valueOf(userId));

        return responseDto;
    }



    @PostMapping(value = "/nickname")
    public ResponseDto emitNickname(HttpServletRequest request, @RequestBody ProduceNicknameRequest produceNicknameRequest){

        String userId = jwtTokenProvider.getUserId(request);

        ResponseDto responseDto = userService.emitNickname(Long.valueOf(userId), produceNicknameRequest.getNickName());

        return responseDto;
    }


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
