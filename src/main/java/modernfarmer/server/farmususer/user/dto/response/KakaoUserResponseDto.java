package modernfarmer.server.farmususer.user.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
public class KakaoUserResponseDto {

    private long id;
    @JsonCreator
    public KakaoUserResponseDto(@JsonProperty("id") long id) {
        this.id = id;
    }
//    private UserProfileDTO  kakao_account;
//
//
//    @Setter
//    @Getter
//    public class UserProfileDTO {
//        private boolean profileNicknameNeedsAgreement;
//        private boolean profileImageNeedsAgreement;
//        private ProfileData profile;
//        private boolean hasEmail;
//        private boolean emailNeedsAgreement;
//        private boolean isEmailValid;
//        private boolean isEmailVerified;
//        private String email;
//
//
//
//
//        @Getter
//        @Setter
//        public class ProfileData {
//            private String nickname;
//            private String thumbnailImageUrl;
//            private String profileImageUrl;
//            private boolean isDefaultImage;
//        }
//    }






}