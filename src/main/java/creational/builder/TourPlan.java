package creational.builder;

import java.time.LocalDate;

public class TourPlan {

    private String title;

    private Integer nights;

    private Integer days;

    private LocalDate startDate;

    private String wheretoStay;

    public TourPlan() {
    }

    public TourPlan(String title, Integer nights, Integer days, LocalDate startDate, String wheretoStay) {
        this.title = title;
        this.nights = nights;
        this.days = days;
        this.startDate = startDate;
        this.wheretoStay = wheretoStay;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setWheretoStay(String wheretoStay) {
        this.wheretoStay = wheretoStay;
    }

}
