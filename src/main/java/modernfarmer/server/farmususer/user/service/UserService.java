package modernfarmer.server.farmususer.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.global.config.s3.S3Uploader;
import modernfarmer.server.farmususer.global.exception.fail.ErrorMessage;
import modernfarmer.server.farmususer.global.exception.success.SuccessMessage;
import modernfarmer.server.farmususer.user.dto.response.*;
import modernfarmer.server.farmususer.user.entity.User;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import modernfarmer.server.farmususer.user.util.TimeCalculator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final TimeCalculator timeCalculator;


//    public BaseResponseDto emitProfileImage(Long userId, MultipartFile multipartFile) throws IOException {
//
//        String imageUrl = s3Uploader.uploadFiles(multipartFile, "userprofileimage");
//        log.info(imageUrl);
//
//        userRepository.emitUserProfileImage(userId, imageUrl);
//
//        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);
//    }

    public BaseResponseDto getUser(Long userId){

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return BaseResponseDto.of(ErrorMessage.NO_USER_DATA);
        }

        long dDay = timeCalculator.calFromToday(user.get().getCreatedAt());

        return BaseResponseDto.of(SuccessMessage.SUCCESS, GetUserResponseDto.of(user.get().getNickname(),user.get().getProfileImage(),dDay));
    }



    public BaseResponseDto emitNickname(Long userId, String nickName){

        userRepository.updateUserNickname(nickName, userId);

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);
    }

    public BaseResponseDto selectProfileImageAndNickname(Long userId, MultipartFile multipartFile,
                                                         String nickName) throws IOException {

        if(multipartFile.isEmpty()){

            userRepository.updateUserNickname(nickName,userId);

        }else{
            String imageUrl = s3Uploader.uploadFiles(multipartFile, "userprofileimage");
            log.info(imageUrl);
            userRepository.selectUserProfileAndNickname(userId,imageUrl,nickName);

        }
        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);

    }



    public BaseResponseDto selectProfileImage(Long userId){

        String userProfileImage = userRepository.selectUserProfileImage(userId);

        return BaseResponseDto.of(SuccessMessage.SUCCESS, ProfileImageResponseDto.of(userProfileImage));
    }

    public BaseResponseDto signUpComlete(Long userId){

       userRepository.updateEarly(userId);

       return BaseResponseDto.of(SuccessMessage.SUCCESS, null);
    }


    public BaseResponseDto deleteUser(Long userId){

        userRepository.deleteUser(userId);

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);
    }



     public BaseResponseDto allUser() {

        List<User> userList = userRepository.findAllBy();
         List<AllUserDto> userResponseList = userList.stream()
                 .map(user -> AllUserDto.of(user.getId(), user.getNickname(), user.getProfileImage()))
                 .collect(Collectors.toList());

        return BaseResponseDto.of(SuccessMessage.SUCCESS, AllUserResponseDto.of(userResponseList));

     }

    public BaseResponseDto deleteUserProfile(Long userId) {

        userRepository.updateUserProfileDefault(userId);

        log.info("유저 프로필 삭제 완료");
        return BaseResponseDto.of(SuccessMessage.SUCCESS, null);

    }

    public BaseResponseDto specificUser(Long userId) {


        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return BaseResponseDto.of(ErrorMessage.NO_USER_DATA);
        }

        return BaseResponseDto.of(SuccessMessage.SUCCESS,
                SpecificUserResponseDto.of(user.get().getId(),
                user.get().getNickname(),
                user.get().getProfileImage()));

    }


    public BaseResponseDto logout(Long userId) {

        deleteValueByKey(String.valueOf(userId));

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);
    }

    public BaseResponseDto reissueToken(String refreshToken, Long userId) {

        String redisRefreshToken = redisTemplate.opsForValue().get(userId.toString());

        if(refreshToken.equals(redisRefreshToken)){

            String userRole = userRepository.findUserRole(userId);

            return BaseResponseDto.of(SuccessMessage.SUCCESS,
                    RefreshTokenResponseDto.of(
                            jwtTokenProvider.createAccessToken(userId, userRole),
                            refreshToken
                            ));

        }
        return BaseResponseDto.of(ErrorMessage.REFRESH_NOTIFICATION_ERROR);

    }









    public void deleteValueByKey(String key) {
        redisTemplate.delete(key);
    }

}
