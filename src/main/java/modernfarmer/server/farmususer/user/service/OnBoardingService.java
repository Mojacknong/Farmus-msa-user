package modernfarmer.server.farmususer.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.global.exception.fail.ErrorMessage;
import modernfarmer.server.farmususer.global.exception.success.SuccessMessage;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;


@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class OnBoardingService {
    private final UserRepository userRepository;


    public BaseResponseDto onBoardingMotivation(Long userId, String motivation){

        userRepository.insertUserMotivation(userId, motivation);

        return BaseResponseDto.of(SuccessMessage.SUCCESS,null);

    }

}
