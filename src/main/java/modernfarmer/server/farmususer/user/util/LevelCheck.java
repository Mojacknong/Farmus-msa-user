package modernfarmer.server.farmususer.user.util;


import org.springframework.stereotype.Component;

@Component
public class LevelCheck {

    public String recommendAlgorithms(int time,String skill) {
        boolean isIntermediate = false;
        boolean isMaster = false;
        boolean isElementary = false;
        boolean isBeginner = false;


        if ("홈파밍 중급".equals(skill)) {
            isIntermediate = true;
        } else if ("홈파밍 고수".equals(skill)) {
            isMaster = true;
        } else if ("홈파밍 초보".equals(skill)) {
            isElementary = true;
        } else if ("홈파밍 입문".equals(skill)) {
            isBeginner = true;
        }


        if (time == 2 && (isIntermediate || isMaster)) {
            return "HARD";
        } else if (time == 2 && (isBeginner || isElementary)) {
            return "NORMAL";
        } else if (time == 1 && isMaster) {
            return "HARD";
        } else if (time == 1 && (isIntermediate || isElementary)) {
            return "NORMAL";
        } else if (time == 1 && isBeginner) {
            return "EASY";
        } else if (time == 0 && isMaster) {
            return "HARD";
        } else if (time == 0 && isIntermediate) {
            return "NORMAL";
        } else if (time == 0 && (isElementary || isBeginner)) {
            return "EASY";
        }

        return "알 수 없음";
    }
}
