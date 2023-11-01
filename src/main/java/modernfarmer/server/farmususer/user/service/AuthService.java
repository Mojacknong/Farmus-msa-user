package modernfarmer.server.farmususer.user.service;


import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Service
public class AuthService{


    public JwtTokenProvider jwtTokenProvider;
    public RedisTemplate<String, String> redisTemplate;

    public UserRepository userRepository;

    private final WebClient webClient;


    @Autowired
    public AuthService(WebClient webClient, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> redisTemplate) {
        this.webClient = webClient;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
    }

    public TokenResponseDto googleLogin(String accessToken) {

        User user;
        boolean early = false;

        Mono<GoogleUserResponseDto> userInfoMono = getUserGoogleInfo(accessToken);
        GoogleUserResponseDto userInfo = userInfoMono.block();


        Optional<User> userData = userRepository.findByUserNumber(String.valueOf(userInfo.getId()));

        log.info(String.valueOf(userInfo.getEmail()));
        log.info(String.valueOf(userInfo.getPicture()));
        log.info(String.valueOf(userInfo.getId()));

        if(userData.isEmpty()){
            user = User.builder()
                    .userNumber(String.valueOf(userInfo.getId()))
                    .roles("USER")
                    .profileImage(userInfo.getPicture())
                    .build();
            early = true;

            userRepository.save(user);
        }

        Optional<User> userLoginData = userRepository.findByUserNumber(String.valueOf(userInfo.getId()));


        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());

        TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
                .message("OK")
                .code(200)
                .early(early)
                .accessToken(jwtTokenProvider.createAccessToken(
                        userLoginData.get().getId(),
                        String.valueOf(userLoginData.get().getRoles())))
                .refreshToken(refreshToken)
                .build();


        redisTemplate.opsForValue().set(String.valueOf(userLoginData.get().getId()),refreshToken);


        return tokenResponseDto;
    }

    public TokenResponseDto kakaoLogin(String accessToken) {

        User user;
        boolean early = false;
        Mono<KakaoUserResponseDto> userInfoMono = getUserKakaoInfo(accessToken);
        KakaoUserResponseDto userInfo = userInfoMono.block();


        log.info(String.valueOf(userInfo.getKakao_account().getEmail()));
        log.info(String.valueOf(userInfo.getKakao_account().getProfile().getProfile_image_url()));
        log.info(String.valueOf(userInfo.getKakao_account().getProfile().getNickname()));


        Optional<User> userData = userRepository.findByUserNumber(String.valueOf(userInfo.getId()));

        if(userData.isEmpty()){
            user = User.builder()
                    .userNumber(String.valueOf(userInfo.getId()))
                    .roles("USER")
                    .profileImage(userInfo.getKakao_account().getProfile().getProfile_image_url())
                    .build();

            early = true;

            userRepository.save(user);
        }

        Optional<User> userLoginData = userRepository.findByUserNumber(String.valueOf(userInfo.getId()));


        String refreshToken = jwtTokenProvider.createRefreshToken(userLoginData.get().getId());

        TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
                .message("OK")
                .code(200)
                .early(early)
                .accessToken(jwtTokenProvider.createAccessToken(
                        userLoginData.get().getId(),
                        String.valueOf(userLoginData.get().getRoles())))
                .refreshToken(refreshToken)
                .build();


        redisTemplate.opsForValue().set(String.valueOf(userLoginData.get().getId()),refreshToken);


        return tokenResponseDto;
    }

    public Mono<KakaoUserResponseDto> getUserKakaoInfo(String accessToken) {
        return webClient
                .get()
                .uri("https://kapi.kakao.com/v2/user/me") // 카카오 사용자 정보 엔드포인트
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserResponseDto.class);
    }

    public Mono<GoogleUserResponseDto> getUserGoogleInfo(String accessToken) {
        return webClient
                .get()
                .uri("https://www.googleapis.com/oauth2/v2/userinfo") // 카카오 사용자 정보 엔드포인트
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GoogleUserResponseDto.class);
    }
}