package builder;

import creational.builder.DefaultTourPlanBuilder;
import creational.builder.TourDirector;
import creational.builder.TourPlan;
import creational.builder.TourPlanBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuilderTest {

    @Test
    public void 다양하고_복잡한_객체의_생성방식() {
        TourPlan myTrip = new TourPlan(
                "나만의 여행",
                1,
                2,
                null,
                null
        );

        TourPlan jejuTour = new TourPlan(
                "제주도 3박 4일 패키지 여행",
                3,
                4,
                null,
                "제주 리조트"
        );

        TourPlan shortTrip = new TourPlan();
        shortTrip.setTitle("오레곤 롱비치 여행");
        shortTrip.setNights(3);
        shortTrip.setStartDate(LocalDate.of(2021, 7, 15));

        TourPlan tourPlan = new TourPlan();
        tourPlan.setTitle("칸쿤 여행");
        tourPlan.setNights(2);
        tourPlan.setDays(3);
        tourPlan.setStartDate(LocalDate.of(2022, 1, 23));
        tourPlan.setWheretoStay("리조트");

    }

    @Test
    public void 빌더패턴을_사용하여_동일한_생성과정으로_객체를_생성한다() {
        TourPlanBuilder tourPlanBuilder = new DefaultTourPlanBuilder();
        TourDirector tourDirector = new TourDirector(tourPlanBuilder);

        TourPlan myTrip = tourPlanBuilder
                .title("나만의 여행")
                .nightsAndDays(1, 2)
                .build();

        TourPlan jejuTour = tourDirector.jejuResortPackageTour();

        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    TourPlan shortTrip = tourPlanBuilder
                            .title("오레곤 롱비치 여행")
                            .nightsAndDays(3, null)
                            .startDate(LocalDate.of(2021, 7, 15))
                            .build();
                }
        );

        TourPlan tourPlan = tourPlanBuilder
                .title("칸쿤 여행")
                .nightsAndDays(2, 3)
                .startDate(LocalDate.of(2022, 1, 23))
                .whereToStay("리조트")
                .build();
    }

}
