package modernfarmer.server.farmususer.global.enums;

public class Motivations {

    public enum Motive_1 {
        MOTIVE1_1("채소를 직접 길러\n식비를 줄여보세요!"),
        MOTIVE1_2("직접 기른 채소로\n알뜰하고 현명한 식사를 해봐요!"),
        MOTIVE1_3("오늘도 채솟값\n알뜰하게 아끼는 중!"),
        MOTIVE1_4("홈파밍으로 지출은 줄이고\n성취감은 더해봐요"),
        MOTIVE1_5("직접 기른 채소로\n매일 알뜰하게, 매일 특별하게!");

        private final String motive1;

        Motive_1(String motive1) {
            this.motive1 = motive1;
        }

        public String getMotive() {
            return motive1;
        }
    }
    public enum Motive_2 {
        MOTIVE2_1("직접 기른 채소를\n먹을 수 있는 그날까지!"),
        MOTIVE2_2("신선함으로 가득 채워지는 식탁, \n 늘 함께할게요!"),
        MOTIVE2_3("눈으로 보는 재미, 입으로 맛보는\n홈파밍의 즐거움!");

        private final String motive2;

        Motive_2(String motive2) {
            this.motive2 = motive2;
        }

        public String getMotive() {
            return motive2;
        }
    }
    public enum Motive_3 {
        MOTIVE3_1("바쁜 일상에서 잠깐 벗어나\n텃밭을 가꿔봐요"),
        MOTIVE3_2("홈파밍을 하며\n마음의 안정을 느껴봐요!"),
        MOTIVE3_3("스트레스 받는 하루, \n 홈파밍으로 힐링하세요"),
        MOTIVE3_4("나만의 채소를 키우며\n소소한 성취감을 느껴보세요"),
        MOTIVE3_5("성취감은 꾸준함에서! \n 오늘도 함께 성장해요");

        private final String motive3;

        Motive_3(String motive3) {
            this.motive3 = motive3;
        }

        public String getMotive() {
            return motive3;
        }
    }
}
