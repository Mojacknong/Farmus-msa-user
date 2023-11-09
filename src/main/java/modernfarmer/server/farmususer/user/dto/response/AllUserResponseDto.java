package modernfarmer.server.farmususer.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class AllUserResponseDto {
    List<AllUserDto> allUserDtoList;
}
