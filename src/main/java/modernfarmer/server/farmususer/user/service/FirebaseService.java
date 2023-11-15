package modernfarmer.server.farmususer.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.global.config.s3.S3Uploader;
import modernfarmer.server.farmususer.global.exception.success.SuccessMessage;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.entity.User;
import modernfarmer.server.farmususer.user.entity.UserFirebaseToken;
import modernfarmer.server.farmususer.user.repository.UserFirebaseTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class FirebaseService {

    private final UserFirebaseTokenRepository userFirebaseTokenRepository;


    public BaseResponseDto insertFirebaseToken(Long userId, String firebaseToken) {


        UserFirebaseToken userFirebaseToken = UserFirebaseToken.builder()
                .user(User.builder().id(userId).build())
                .token(firebaseToken)
                .build();

        userFirebaseTokenRepository.save(userFirebaseToken);
        log.info("파이어 베이스 토큰 삽입 완료");
        return BaseResponseDto.of(SuccessMessage.SUCCESS, null);
    }

    public BaseResponseDto deleteFirebaseToken(Long userId, String firebaseToken) {


        userFirebaseTokenRepository.deleteFirebaseToken(
                User.builder()
                        .id(userId)
                        .build(),
                firebaseToken
                );

        log.info("파이어 베이스 토큰 삭제 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS, null);
    }
}
