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

    public BaseResponseDto onBoardingMotivation(Long userId, OnBoardingMotivationRequest onBoardingMotivationRequest){

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

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);

    }

    public BaseResponseDto onBoardingLevel(Long userId, OnBoardingLevelRequest onBoardingLevelRequest){

        String level = recommendAlgorithms(onBoardingLevelRequest.getTime(), onBoardingLevelRequest.getSkill());

        userRepository.insertUserLevel(userId, level);

        return BaseResponseDto.of(SuccessMessage.SUCCESS, OnBoardingLevelResponse.of(level));

    }

    private String recommendAlgorithms(int time,String skill) {
        boolean isIntermediate = false;
        boolean isMaster = false;
        boolean isElementary = false;
        boolean isBeginner = false;


        if ("홈파밍 중급".equals(skill)) {
            isIntermediate = true;
        } else if ("홈파밍 고수".equals(skill)) {
            isMaster = true;
        } else if ("홈파밍 초보".equals(skill)) {
            isElementary = true;
        } else if ("홈파밍 입문".equals(skill)) {
            isBeginner = true;
        }


        if (time == 2 && (isIntermediate || isMaster)) {
            return "HARD";
        } else if (time == 2 && (isBeginner || isElementary)) {
            return "NORMAL";
        } else if (time == 1 && isMaster) {
            return "HARD";
        } else if (time == 1 && (isIntermediate || isElementary)) {
            return "NORMAL";
        } else if (time == 1 && isBeginner) {
            return "EASY";
        } else if (time == 0 && isMaster) {
            return "HARD";
        } else if (time == 0 && isIntermediate) {
            return "NORMAL";
        } else if (time == 0 && (isElementary || isBeginner)) {
            return "EASY";
        }

        return "알 수 없음";
    }









}
