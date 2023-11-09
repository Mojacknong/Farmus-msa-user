package modernfarmer.server.farmususer.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.global.exception.fail.ErrorMessage;
import modernfarmer.server.farmususer.global.exception.success.SuccessMessage;
import modernfarmer.server.farmususer.user.dto.request.OnBoardingLevelRequest;
import modernfarmer.server.farmususer.user.dto.request.OnBoardingMotivationRequest;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.dto.response.OnBoardingLevelResponse;
import modernfarmer.server.farmususer.user.entity.User;
import modernfarmer.server.farmususer.user.entity.UserMotivation;
import modernfarmer.server.farmususer.user.repository.UserMotivationRepository;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;


@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class OnBoardingService {

    private final UserRepository userRepository;

    private final UserMotivationRepository userMotivationRepository;

    public BaseResponseDto onBoardingMotivation(Long userId, OnBoardingMotivationRequest onBoardingMotivationRequest){

        User user = User.builder().id(userId).build();

        for(String motivations : onBoardingMotivationRequest.getMotivation()){

            UserMotivation userMotivation = UserMotivation
                    .builder()
                    .user(user)
                    .userMotivation(motivations)
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
        boolean isAdvanced = false;
        boolean isExperienced = false;
        boolean isBeginner = false;


        if ("중급자".equals(skill)) {
            isIntermediate = true;
        } else if ("고수".equals(skill)) {
            isAdvanced = true;
        } else if ("경험자".equals(skill)) {
            isExperienced = true;
        } else if ("초급자".equals(skill)) {
            isBeginner = true;
        }


        if (time == 2 && (isIntermediate || isAdvanced)) {
            return "HARD";
        } else if (time == 2 && (isBeginner || isExperienced)) {
            return "NORMAL";
        } else if (time == 1 && isAdvanced) {
            return "HARD";
        } else if (time == 1 && (isIntermediate || isExperienced)) {
            return "NORMAL";
        } else if (time == 1 && isBeginner) {
            return "EASY";
        } else if (time == 0 && (isExperienced && isBeginner)) {
            return "EASY";
        } else if (time == 0 && isExperienced) {
            return "NORMAL";

        } else if (time == 0 && isAdvanced) {
            return "HARD";
        }

        return "알 수 없음";
    }









}
