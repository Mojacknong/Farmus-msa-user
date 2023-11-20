package modernfarmer.server.farmususer.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.global.exception.fail.ErrorMessage;
import modernfarmer.server.farmususer.global.exception.success.SuccessMessage;
import modernfarmer.server.farmususer.user.dto.request.OnBoardingLevelRequest;
import modernfarmer.server.farmususer.user.dto.request.OnBoardingMotivationRequest;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.dto.response.OnBoardingLevelResponse;
import modernfarmer.server.farmususer.user.entity.Motivation;
import modernfarmer.server.farmususer.user.entity.User;
import modernfarmer.server.farmususer.user.entity.UserMotivation;
import modernfarmer.server.farmususer.user.repository.MotivationRepository;
import modernfarmer.server.farmususer.user.repository.UserMotivationRepository;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import modernfarmer.server.farmususer.user.util.LevelCheck;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.Optional;


@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class OnBoardingService {

    private final UserRepository userRepository;

    private final UserMotivationRepository userMotivationRepository;

    private final MotivationRepository motivationRepository;

    private final LevelCheck levelCheck;

    public BaseResponseDto<Void> onBoardingMotivation(Long userId, OnBoardingMotivationRequest onBoardingMotivationRequest){

        User user = User.builder().id(userId).build();

        for(String motivations : onBoardingMotivationRequest.getMotivation()){

            Optional<Motivation> motivation = motivationRepository.findByMotivationReason(motivations);

            if(motivation.isEmpty()){
                return BaseResponseDto.of(ErrorMessage.NO_MOTIVATION_DATA);
            }

            UserMotivation userMotivation = UserMotivation
                    .builder()
                    .user(user)
                    .motivation(motivation.get())
                    .build();

            userMotivationRepository.save(userMotivation);
        }

        log.info("동기 데이터 삽입 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);

    }

    public BaseResponseDto<OnBoardingLevelResponse> onBoardingLevel(Long userId, OnBoardingLevelRequest onBoardingLevelRequest){

        String level = levelCheck.recommendAlgorithms(onBoardingLevelRequest.getTime(), onBoardingLevelRequest.getSkill());

        userRepository.insertUserLevel(userId, level);

        log.info("난이도 데이터 삽입 완료");

        return BaseResponseDto.of(SuccessMessage.SUCCESS, OnBoardingLevelResponse.of(level));

    }
}
