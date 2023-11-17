package modernfarmer.server.farmususer.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.global.exception.fail.ErrorMessage;
import modernfarmer.server.farmususer.global.exception.success.SuccessMessage;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.dto.response.GoogleUserResponseDto;
import modernfarmer.server.farmususer.user.dto.response.KakaoUserResponseDto;
import modernfarmer.server.farmususer.user.dto.response.TokenResponseDto;
import modernfarmer.server.farmususer.user.entity.User;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import modernfarmer.server.farmususer.user.util.SocialLogin;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;



@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService{


    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, String> redisTemplate;

    private final UserRepository userRepository;

    private final SocialLogin socialLogin;


    public BaseResponseDto<TokenResponseDto> googleLogin(String accessToken) {

        User user;
        boolean early;

        Mono<GoogleUserResponseDto> userInfoMono = socialLogin.getUserInfo(accessToken, "https://www.googleapis.com/oauth2/v2/userinfo", GoogleUserResponseDto.class);
        GoogleUserResponseDto userInfo = userInfoMono.block();

        if(userInfo == null){

            return BaseResponseDto.of(ErrorMessage.FAIL_GET_INFORMATION);

        }

        Optional<User> userData = userRepository.findByUserNumber(String.valueOf(userInfo.getId()));

        if(userData.isEmpty()){
            user = User.builder()
                    .userNumber(String.valueOf(userInfo.getId()))
                    .roles("USER")
                    .early(true)
                    .build();

            userRepository.save(user);
        }

        Optional<User> userLoginData = userRepository.findByUserNumber(String.valueOf(userInfo.getId()));

        if(userLoginData.isEmpty()){

            return BaseResponseDto.of(ErrorMessage.FAIL_GET_INFORMATION);

        }

        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());

        early = userLoginData.get().isEarly();

        redisTemplate.opsForValue().set(String.valueOf(userLoginData.get().getId()),refreshToken);

        log.info("구글 로그인 완료");

        return BaseResponseDto.of(
                SuccessMessage.SUCCESS,
                TokenResponseDto.of(
                        jwtTokenProvider.createAccessToken(
                                userLoginData.get().getId(),
                                String.valueOf(userLoginData.get().getRoles())),
                        refreshToken,
                        early
                )
        );
    }

    public BaseResponseDto<TokenResponseDto> kakaoLogin(String accessToken) {

        User user;
        boolean early;
        Mono<KakaoUserResponseDto> userInfoMono = socialLogin.getUserInfo(accessToken, "https://kapi.kakao.com/v2/user/me", KakaoUserResponseDto.class);
        KakaoUserResponseDto userInfo = userInfoMono.block();

        if(userInfo == null){

            return BaseResponseDto.of(ErrorMessage.FAIL_GET_INFORMATION);

        }

        Optional<User> userData = userRepository.findByUserNumber(String.valueOf(userInfo.getId()));


        if(userData.isEmpty()){
            user = User.builder()
                    .userNumber(String.valueOf(userInfo.getId()))
                    .roles("USER")
                    .early(true)
                    .build();

            userRepository.save(user);
        }

        Optional<User> userLoginData = userRepository.findByUserNumber(String.valueOf(userInfo.getId()));

        if(userLoginData.isEmpty()){

            return BaseResponseDto.of(ErrorMessage.FAIL_GET_INFORMATION);

        }

        early = userLoginData.get().isEarly();

        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());


        redisTemplate.opsForValue().set(String.valueOf(userLoginData.get().getId()),refreshToken);

        log.info("카카오 로그인 완료");

        return BaseResponseDto.of(
                SuccessMessage.SUCCESS,
                TokenResponseDto.of(
                        jwtTokenProvider.createAccessToken(
                                userLoginData.get().getId(),
                                String.valueOf(userLoginData.get().getRoles())),
                        refreshToken,
                        early
                )
        );
    }

}