package modernfarmer.server.farmususer.farm;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://3.36.221.140:8081", name = "farm-service")
public interface FarmServiceFeignClient {

    @DeleteMapping("/api/veggie/{userId}")
    void deleteAllVeggies(@PathVariable("userId") Long userId);

}
