package modernfarmer.server.farmususer.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.global.exception.success.SuccessMessage;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.dto.response.GoogleUserResponseDto;
import modernfarmer.server.farmususer.user.dto.response.KakaoUserResponseDto;
import modernfarmer.server.farmususer.user.dto.response.TokenResponseDto;
import modernfarmer.server.farmususer.user.entity.User;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;



@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService{


    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    private final UserRepository userRepository;

    private final WebClient webClient;


    public BaseResponseDto googleLogin(String accessToken) {

        User user;
        boolean early;

        Mono<GoogleUserResponseDto> userInfoMono = getUserInfo(accessToken, "https://www.googleapis.com/oauth2/v2/userinfo", GoogleUserResponseDto.class);
        GoogleUserResponseDto userInfo = userInfoMono.block();

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

        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());

        early = userLoginData.get().isEarly();

        BaseResponseDto baseResponseDto = BaseResponseDto.of(
                SuccessMessage.SUCCESS,
                TokenResponseDto.of(
                        jwtTokenProvider.createAccessToken(
                                userLoginData.get().getId(),
                                String.valueOf(userLoginData.get().getRoles())),
                        refreshToken,
                        early
                )
        );

        redisTemplate.opsForValue().set(String.valueOf(userLoginData.get().getId()),refreshToken);

        return baseResponseDto;
    }

    public BaseResponseDto kakaoLogin(String accessToken) {

        User user;
        boolean early;
        Mono<KakaoUserResponseDto> userInfoMono = getUserInfo(accessToken, "https://kapi.kakao.com/v2/user/me", KakaoUserResponseDto.class);
        KakaoUserResponseDto userInfo = userInfoMono.block();

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

        early = userLoginData.get().isEarly();

        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());

        BaseResponseDto baseResponseDto = BaseResponseDto.of(
                SuccessMessage.SUCCESS,
                TokenResponseDto.of(
                    jwtTokenProvider.createAccessToken(
                        userLoginData.get().getId(),
                        String.valueOf(userLoginData.get().getRoles())),
                    refreshToken,
                    early
                )
        );

        redisTemplate.opsForValue().set(String.valueOf(userLoginData.get().getId()),refreshToken);

        return baseResponseDto;
    }

    public <T> Mono<T> getUserInfo(String accessToken, String apiUrl, Class<T> responseType) {
        return webClient
                .get()
                .uri(apiUrl)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(responseType);
    }

}