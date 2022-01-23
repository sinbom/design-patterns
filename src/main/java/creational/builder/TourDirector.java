package creational.builder;

public class TourDirector {

    private final TourPlanBuilder tourPlanBuilder;

    public TourDirector(TourPlanBuilder tourPlanBuilder) {
        this.tourPlanBuilder = tourPlanBuilder;
    }

    public TourPlan jejuResortPackageTour() {
        return tourPlanBuilder
                .title("제주도 3박 4일 패키지 여행")
                .nightsAndDays(3, 4)
                .whereToStay("제주 리조트")
                .build();
    }

}
