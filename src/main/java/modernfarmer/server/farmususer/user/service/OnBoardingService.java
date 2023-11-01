package modernfarmer.server.farmususer.user.service;


import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.user.dto.response.ResponseDto;
import modernfarmer.server.farmususer.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

@Transactional
@Slf4j
@Service
public class OnBoardingService {
    public UserRepository userRepository;

    @Autowired
    public OnBoardingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseDto onBoardingMotivation(Long userId, String motivation){

        userRepository.insertUserMotivation(userId, motivation);

        ResponseDto responseDto = ResponseDto.builder()
                .message("OK")
                .code(200)
                .build();

        return responseDto;
    }

}
