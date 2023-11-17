package modernfarmer.server.farmususer.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.user.dto.request.ProduceNicknameRequest;
import modernfarmer.server.farmususer.user.dto.response.*;

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

    @GetMapping
    public BaseResponseDto<GetUserResponseDto> getUser(HttpServletRequest request){

        String userId = jwtTokenProvider.getUserId(request);

        return userService.getUser(Long.valueOf(userId));
    }



    @PostMapping(value = "/nickname")
    public BaseResponseDto<Void> emitNickname(HttpServletRequest request, @RequestBody ProduceNicknameRequest produceNicknameRequest){

        String userId = jwtTokenProvider.getUserId(request);

        return userService.emitNickname(Long.valueOf(userId), produceNicknameRequest.getNickName());
    }

    @PostMapping(value = "/select-information", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponseDto<Void> selectProfileImageAndNickname(HttpServletRequest request, @RequestPart(value = "file", required = false) MultipartFile multipartFile, @RequestParam("nickName")String nickName) throws IOException {

        String userId = jwtTokenProvider.getUserId(request);

        return  userService.selectProfileImageAndNickname(Long.valueOf(userId), multipartFile,nickName);

    }

    @PatchMapping(value = "/sign-up/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponseDto<Void> signUpComplete(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);

        return userService.signUpComlete(Long.valueOf(userId));

    }

    @GetMapping(value = "/profileImage")
    public BaseResponseDto<ProfileImageResponseDto> selectProfileImage(HttpServletRequest request){

        String userId = jwtTokenProvider.getUserId(request);

        return userService.selectProfileImage(Long.valueOf(userId));
    }


    @DeleteMapping(value = "/delete")
    public BaseResponseDto<Void> deleteUser(HttpServletRequest request){

        String userId = jwtTokenProvider.getUserId(request);

        return userService.deleteUser(Long.valueOf(userId));
    }

    @DeleteMapping("/logout")
    public BaseResponseDto<Void> logout(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);

        return userService.logout(Long.valueOf(userId));
    }

    @GetMapping(value = "/reissue-token")
    public BaseResponseDto<RefreshTokenResponseDto> reissueToken(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);
        String refreshToken = jwtTokenProvider.resolveToken(request);

        return userService.reissueToken(refreshToken, Long.valueOf(userId));
    }

    @PatchMapping(value = "/delete/user-profile")
    public BaseResponseDto<Void> deleteUserProfile(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);

        return userService.deleteUserProfile(Long.valueOf(userId));
    }


    @GetMapping(value = "/all-user")
    public BaseResponseDto<AllUserResponseDto> allUser()  {

        return userService.allUser();
    }

    @GetMapping(value = "/specific-user")
    public BaseResponseDto<SpecificUserResponseDto> specificUser(@RequestParam("userId") Long userId)  {

        return userService.specificUser(userId);
    }

}
