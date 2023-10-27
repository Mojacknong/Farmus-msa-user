package modernfarmer.server.farmususer.user.service;

import modernfarmer.server.farmususer.user.dto.response.ResponseDto;
import modernfarmer.server.farmususer.user.dto.response.TokenResponseDto;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;


@Service
public class UserService {

    public JwtTokenProvider jwtTokenProvider;
    public RedisTemplate<String, String> redisTemplate;

    public UserRepository userRepository;

    private final WebClient webClient;

    @Autowired
    public UserService(WebClient webClient, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> redisTemplate) {
        this.webClient = webClient;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
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

        reissueTokenResponse = TokenResponseDto.builder()
                .code(403)
                .message("접근이 올바르지 않습니다.")
                .build();

        return reissueTokenResponse;

    }

    public void deleteValueByKey(String key) {
        redisTemplate.delete(key);
    }

}
