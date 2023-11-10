package modernfarmer.server.farmususer.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.user.dto.request.ProduceNicknameRequest;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;

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

//    @PostMapping(value = "/profileImage", produces = MediaType.APPLICATION_JSON_VALUE)
//    public BaseResponseDto produceProfileImage(HttpServletRequest request, @RequestPart("file") MultipartFile multipartFile) throws IOException {
//
//        String userId = jwtTokenProvider.getUserId(request);
//        log.info(userId);
//        BaseResponseDto responseDto = userService.emitProfileImage(Long.valueOf(userId), multipartFile);
//
//        return responseDto;
//
//    }


    @PostMapping(value = "/nickname")
    public BaseResponseDto emitNickname(HttpServletRequest request, @RequestBody ProduceNicknameRequest produceNicknameRequest){

        String userId = jwtTokenProvider.getUserId(request);

        BaseResponseDto responseDto = userService.emitNickname(Long.valueOf(userId), produceNicknameRequest.getNickName());

        return responseDto;
    }

    @PostMapping(value = "/select-information", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponseDto selectProfileImageAndNickname(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile multipartFile, @RequestParam("nickName")String nickName) throws IOException {

        String userId = jwtTokenProvider.getUserId(request);
        log.info(userId);
        BaseResponseDto responseDto = userService.selectProfileImageAndNickname(Long.valueOf(userId), multipartFile,nickName);

        return responseDto;

    }

    @PatchMapping(value = "/sign-up/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponseDto signUpComplete(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);


        BaseResponseDto responseDto = userService.signUpComlete(Long.valueOf(userId));

        return responseDto;

    }

    @GetMapping(value = "/profileImage")
    public BaseResponseDto selectProfileImage(HttpServletRequest request){

        String userId = jwtTokenProvider.getUserId(request);


        BaseResponseDto profileImageResponseDto = userService.selectProfileImage(Long.valueOf(userId));

        return profileImageResponseDto;
    }


    @DeleteMapping(value = "/delete")
    public BaseResponseDto deleteUser(HttpServletRequest request){

        String userId = jwtTokenProvider.getUserId(request);

        BaseResponseDto responseDto = userService.deleteUser(Long.valueOf(userId));

        return responseDto;
    }






    @DeleteMapping("/logout")
    public BaseResponseDto logout(HttpServletRequest request)  {


        String userId = jwtTokenProvider.getUserId(request);

        BaseResponseDto logoutResponseDto = userService.logout(Long.valueOf(userId));

        log.info("로그아웃 완료");

        return logoutResponseDto;
    }

    @GetMapping(value = "/reissue-token")
    public BaseResponseDto reissueToken(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);
        String refreshToken = jwtTokenProvider.resolveToken(request);

        BaseResponseDto reissueTokenResponseDto = userService.reissueToken(refreshToken, Long.valueOf(userId));

        log.info("토큰 재발급 완료");

        return reissueTokenResponseDto;
    }

    @GetMapping(value = "/all-user")
    public BaseResponseDto allUser()  {

        BaseResponseDto reissueTokenResponseDto = userService.allUser();

        return reissueTokenResponseDto;
    }

    @GetMapping(value = "/specific-user")
    public BaseResponseDto specificUser(@RequestParam("userId") Long userId)  {

       // String userId = jwtTokenProvider.getUserId(request);
        return userService.specificUser(userId);
    }



}
