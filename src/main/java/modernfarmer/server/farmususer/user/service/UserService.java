package modernfarmer.server.farmususer.user.service;

import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.global.config.s3.AmazonS3ResourceStorage;
import modernfarmer.server.farmususer.global.config.s3.FileDetail;
import modernfarmer.server.farmususer.global.exception.notfound.NotFoundRefreshTokenException;
import modernfarmer.server.farmususer.user.dto.response.ProfileImageResponseDto;
import modernfarmer.server.farmususer.user.dto.response.ResponseDto;
import modernfarmer.server.farmususer.user.dto.response.TokenResponseDto;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@Service
public class UserService {

    public JwtTokenProvider jwtTokenProvider;
    public RedisTemplate<String, String> redisTemplate;
    public UserRepository userRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    @Autowired
    public UserService(WebClient webClient, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> redisTemplate, AmazonS3ResourceStorage amazonS3ResourceStoraget) {

        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
        this.amazonS3ResourceStorage = amazonS3ResourceStoraget;
    }


    public ResponseDto produceProfileImage(Long userId, MultipartFile multipartFile){


        FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(fileDetail.getPath(), multipartFile);

        log.info(String.valueOf(fileDetail));


        ResponseDto responseDto = ResponseDto
                .builder()
                .code(200)
                .message("OK")
                .build();

        return responseDto;
    }

    public ProfileImageResponseDto selectProfileImage(Long userId){

        String userProfileImage = userRepository.selectUserProfileImage(userId);


        ProfileImageResponseDto profileImageResponseDto = ProfileImageResponseDto
                .builder()
                .code(200)
                .message("OK")
                .profileImage(userProfileImage)
                .build();


        return profileImageResponseDto;
    }







    public ResponseDto deleteUser(Long userId){

        userRepository.deleteUser(userId);

        ResponseDto responseDto = ResponseDto
                .builder()
                .code(200)
                .message("OK")
                .build();

        return responseDto;
    }


    public ResponseDto produceNickname(Long userId, String nickName){

        userRepository.updateUserNickname(nickName, userId);

        ResponseDto responseDto = ResponseDto.builder()
                .code(200)
                .message("OK")
                .build();

        return responseDto;
    }


    public ResponseDto logout(Long userId) {
        deleteValueByKey(String.valueOf(userId));

        ResponseDto responseDto = ResponseDto.builder()
                .message("OK")
                .code(200)
                .build();
        return responseDto;
    }

    public TokenResponseDto reissueToken(String refreshToken, Long userId) {
        TokenResponseDto reissueTokenResponse;

        String redisRefreshToken = redisTemplate.opsForValue().get(userId.toString());

        if(refreshToken.equals(redisRefreshToken)){

            String userRole = userRepository.findUserRole(userId);

            reissueTokenResponse= TokenResponseDto
                    .builder()
                    .code(200)
                    .message("OK")
                    .accessToken(jwtTokenProvider.createAccessToken(userId, userRole))
                    .refreshToken(refreshToken)
                    .build();

            return reissueTokenResponse;


        }
        throw new NotFoundRefreshTokenException();

    }

    public void deleteValueByKey(String key) {
        redisTemplate.delete(key);
    }

}
