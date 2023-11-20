package modernfarmer.server.farmususer.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.community.CommunityServiceFeignClient;
import modernfarmer.server.farmususer.farm.FarmServiceFeignClient;
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
    private final CommunityServiceFeignClient communityServiceFeignClient;
    private final FarmServiceFeignClient farmServiceFeignClient;


//    public BaseResponseDto emitProfileImage(Long userId, MultipartFile multipartFile) throws IOException {
//
//        String imageUrl = s3Uploader.uploadFiles(multipartFile, "userprofileimage");
//        log.info(imageUrl);
//
//        userRepository.emitUserProfileImage(userId, imageUrl);
//
//        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);
//    }

    public BaseResponseDto<GetUserResponseDto> getUser(Long userId){

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return BaseResponseDto.of(ErrorMessage.NO_USER_DATA);
        }

        long dDay = timeCalculator.calFromToday(user.get().getCreatedAt());

        log.info("특정 유저 정보 가져오기 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS, GetUserResponseDto.of(user.get().getNickname(),user.get().getProfileImage(),dDay));
    }



    public BaseResponseDto<Void> emitNickname(Long userId, String nickName){

        userRepository.updateUserNickname(nickName, userId);

        log.info("닉네임 수정 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);
    }

    public BaseResponseDto<Void> selectProfileImageAndNickname(Long userId, MultipartFile multipartFile,
                                                         String nickName) throws IOException {

        if(multipartFile.isEmpty()){

            userRepository.updateUserNickname(nickName,userId);

        }else{
            String imageUrl = s3Uploader.uploadFiles(multipartFile, "userprofileimage");
            log.info(imageUrl);
            userRepository.selectUserProfileAndNickname(userId,imageUrl,nickName);

        }

        log.info("유저 프로필 정보 수정 완료");
        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);

    }



    public BaseResponseDto<ProfileImageResponseDto> selectProfileImage(Long userId){

        String userProfileImage = userRepository.selectUserProfileImage(userId);

        log.info("유저 프로필 이미지 조회 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS, ProfileImageResponseDto.of(userProfileImage));
    }

    public BaseResponseDto<Void> signUpComlete(Long userId){

       userRepository.updateEarly(userId);

        log.info("유저 최종 회원가입 완료");

       return BaseResponseDto.of(SuccessMessage.SUCCESS, null);
    }


    public BaseResponseDto<Void> deleteUser(Long userId){

        userRepository.deleteUser(userId);
        communityServiceFeignClient.deleteAllPosting(userId);
    //    farmServiceFeignClient.deleteAllVeggies(userId);






        log.info("유저 계정 삭제 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);
    }



     public BaseResponseDto<AllUserResponseDto> allUser() {

        List<User> userList = userRepository.findAllBy();
         List<AllUserDto> userResponseList = userList.stream()
                 .map(user -> AllUserDto.of(user.getId(), user.getNickname(), user.getProfileImage()))
                 .collect(Collectors.toList());

         log.info("모든 유저 데이터 조회 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS, AllUserResponseDto.of(userResponseList));

     }

    public BaseResponseDto<Void> deleteUserProfile(Long userId) {

        userRepository.updateUserProfileDefault(userId);

        log.info("유저 프로필 삭제 완료");
        return BaseResponseDto.of(SuccessMessage.SUCCESS, null);

    }

    public BaseResponseDto<SpecificUserResponseDto> specificUser(Long userId) {


        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            return BaseResponseDto.of(ErrorMessage.NO_USER_DATA);
        }

        log.info("특정 유저 정보 조회 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS,
                SpecificUserResponseDto.of(user.get().getId(),
                user.get().getNickname(),
                user.get().getProfileImage()));

    }


    public BaseResponseDto<Void> logout(Long userId) {

        deleteValueByKey(String.valueOf(userId));

        log.info("로그아웃 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);
    }

    public BaseResponseDto<RefreshTokenResponseDto> reissueToken(String refreshToken, Long userId) {

        String redisRefreshToken = redisTemplate.opsForValue().get(userId.toString());

        if(refreshToken.equals(redisRefreshToken)){

            String userRole = userRepository.findUserRole(userId);

            log.info("토큰 재발급 완료");

            return BaseResponseDto.of(SuccessMessage.SUCCESS,
                    RefreshTokenResponseDto.of(
                            jwtTokenProvider.createAccessToken(userId, userRole),
                            refreshToken
                            ));

        }
        return BaseResponseDto.of(ErrorMessage.REFRESH_NOTIFICATION_ERROR);

    }

    public String getUserLevel(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        return user.getLevel();
    }

    public GetUserLevelAndNicknameResponseDto getUserLevelAndNickname(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        return GetUserLevelAndNicknameResponseDto.of(user.getLevel(), user.getNickname());
    }







    public void deleteValueByKey(String key) {
        redisTemplate.delete(key);
    }

}
