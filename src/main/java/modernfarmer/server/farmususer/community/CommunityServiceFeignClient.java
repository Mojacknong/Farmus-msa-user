package modernfarmer.server.farmususer.community;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "community-service")
public interface CommunityServiceFeignClient {

    @DeleteMapping("/api/community/posting/all-posting")
    Void deleteAllPosting(Long userId);




}
