package modernfarmer.server.farmususer.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.user.dto.request.OnBoardingLevelRequest;
import modernfarmer.server.farmususer.user.dto.request.OnBoardingMotivationRequest;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.service.OnBoardingService;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/user/on-boarding")
public class OnBoardingController {

    private final JwtTokenProvider jwtTokenProvider;
    private final OnBoardingService onBoardingService;


    @PostMapping(value = "/motivation")
    public BaseResponseDto onBoardingMotivation(HttpServletRequest request, @Validated @RequestBody OnBoardingMotivationRequest onBoardingMotivationRequest)  {

        String userId = jwtTokenProvider.getUserId(request);

        BaseResponseDto responseDto = onBoardingService.onBoardingMotivation(Long.valueOf(userId),onBoardingMotivationRequest);

        return responseDto;
    }

    @PostMapping(value = "/level")
    public BaseResponseDto onBoardingLevel(HttpServletRequest request, @Validated @RequestBody OnBoardingLevelRequest onBoardingLevelRequest)  {

        String userId = jwtTokenProvider.getUserId(request);

        BaseResponseDto responseDto = onBoardingService.onBoardingLevel(Long.valueOf(userId), onBoardingLevelRequest);

        return responseDto;
    }
}
