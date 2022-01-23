package creational.builder;

import java.time.LocalDate;

public class DefaultTourPlanBuilder implements TourPlanBuilder {

    private String title;

    private Integer nights;

    private Integer days;

    private LocalDate startDate;

    private String wheretoStay;

    @Override
    public TourPlanBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public TourPlanBuilder nightsAndDays(Integer nights, Integer days) {
        this.nights = nights;
        this.days = days;
        return this;
    }

    @Override
    public TourPlanBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    @Override
    public TourPlanBuilder whereToStay(String whereToStay) {
        this.wheretoStay = whereToStay;
        return this;
    }

    @Override
    public TourPlan build() {
        if (title == null || title.isEmpty() || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        if (nights != null && days == null || nights == null && days != null) {
            throw new IllegalArgumentException("both a and b must exist or not both exist.");
        }
        return new TourPlan(title, nights, days, startDate, wheretoStay);
    }

}
