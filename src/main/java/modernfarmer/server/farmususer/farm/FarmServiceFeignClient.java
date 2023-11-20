package modernfarmer.server.farmususer.farm;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "farm-service", url = "http://3.36.221.140:8081")
public interface FarmServiceFeignClient {

    @DeleteMapping("/api/veggie/{userId}")
    Void deleteAllFarm(@PathVariable("userId") Long userId);

}
