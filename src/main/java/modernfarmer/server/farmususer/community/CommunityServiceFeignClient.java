package modernfarmer.server.farmususer.community;


import modernfarmer.server.farmususer.user.dto.response.BaseResponseDto;
import modernfarmer.server.farmususer.user.entity.BaseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://3.38.2.59:8082", name = "community-service")
public interface CommunityServiceFeignClient {

    @DeleteMapping("/api/community/posting/all-posting/{userId}")
    void deleteAllPosting(@PathVariable("userId") Long userId);




}
