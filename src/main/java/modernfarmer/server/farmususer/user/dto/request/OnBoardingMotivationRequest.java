package modernfarmer.server.farmususer.user.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OnBoardingMotivationRequest {


    @NotNull(message = "null 값을 가지면 안됩니다.")
    private ArrayList<String> motivation;
}
