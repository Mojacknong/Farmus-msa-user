package modernfarmer.server.farmususer.user.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Component
public class TimeCalculator {

    public long calFromToday(LocalDateTime date){

        LocalDateTime currentDateTime = LocalDateTime.now();

        // 날짜 차이 계산
        long daysDifference = ChronoUnit.DAYS.between(date.toLocalDate(), currentDateTime.toLocalDate());


        return daysDifference;

    }


}
