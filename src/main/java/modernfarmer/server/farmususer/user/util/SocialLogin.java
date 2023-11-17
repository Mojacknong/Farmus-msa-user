package modernfarmer.server.farmususer.user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class SocialLogin {

    private final WebClient webClient;

    public <T> Mono<T> getUserInfo(String accessToken, String apiUrl, Class<T> responseType) {
        return webClient
                .get()
                .uri(apiUrl)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(responseType);
    }
}
