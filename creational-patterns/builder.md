# Creational Patterns(생성 관련 패턴)

## Builder (빌더 패턴)
> 복잡한 객체 생성 방식을 정의하는 클래스와 표현하는 방법을 정의하는 클래스를 별도로 분리하여
> 동일한 생성 절차를 통해 객체를 생성할 수 있도록 제공합니다. 동일한 생성 절차를 통해
> 다양한 객체의 생성 방식에 유연하게 대응할 수 있습니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/creational/builder.png?raw=true"/>
</p>

### 구현 방법

```java
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
```

빌더 패턴을 사용하여 생성할 객체를 정의합니다. 빌더 구현체로 부터 주입받을
필드들을 매개변수로 가지는 생성자가 필요합니다.

```java
public interface TourPlanBuilder {

    TourPlanBuilder title(String title);

    TourPlanBuilder nightsAndDays(Integer nights, Integer days);

    TourPlanBuilder startDate(LocalDate startDate);

    TourPlanBuilder whereToStay(String whereToStay);

    TourPlan build();

}
```

빌더가 주입해야할 필드를 주입하는 메소드 규격을 갖는 인터페이스를 정의합니다. 반환 타입을
인터페이스로 정의하면 메소드 체이닝을 통해 객체 생성 과정에서 편리하게 사용할 수 있습니다. 

```java
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
```

빌더 인터페이스 구현체 클래스를 정의합니다. 구현체를 통해 객체를 생성하는 과정에서 필수 필드나 조건에 따라 필수인 값들을
검사하는 로직을 포함하여 생성되는 객체의 유효성을 검증합니다.

```java
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
```

빌더 패턴을 통해 중복되는 정보를 가진 객체를 반복적으로 생성하는 경우 디렉터를
통해 객체를 생성하도록합니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/creational/builder-diagram.png?raw=true"/>
</p>

인터페이스와 구현체들을 정의한 다이어그램은 다음과 같습니다.

```java
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
```

객체가 가진 필드의 필수 여부가 optional한 경우를 대응하기 위해서는 생성자 오버라이딩 또는 기본 생성자를
사용하여 객체를 생성하고 setter를 사용해서 주입할 수 있습니다. 하지만 객체의 생성 과정이 복잡해지면서 불필요한 코드가
증가하게되고 객체의 유효성을 검증하기 어려워집니다.

```java
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
```

빌더 패턴을 사용하여 동일한 생성 과정으로 다양한 상태 값을 가지는 유효성이
검증된 객체를 유연하게 생성할 수 있습니다.


