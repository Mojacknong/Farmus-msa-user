package modernfarmer.server.farmususer.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.service.FirebaseService;
import modernfarmer.server.farmususer.user.util.JwtTokenProvider;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/firebase")
@RequiredArgsConstructor
public class FirebaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final FirebaseService firebaseService;


    @PostMapping(value = "/firebase-token")
    public BaseResponseDto insertFirebaseToken(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);
        String fireBaseToken = jwtTokenProvider.getFirebaseToken(request);

        return firebaseService.insertFirebaseToken(Long.valueOf(userId),fireBaseToken);
    }


    @DeleteMapping(value = "/firebase-token")
    public BaseResponseDto deleteFirebaseToken(HttpServletRequest request)  {

        String userId = jwtTokenProvider.getUserId(request);
        String fireBaseToken = jwtTokenProvider.getFirebaseToken(request);

        return firebaseService.deleteFirebaseToken(Long.valueOf(userId),fireBaseToken);
    }

}
