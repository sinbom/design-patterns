# Creational Patterns(생성 관련 패턴)

## Prototype (프로토타입 패턴)
> 객체를 생성하는 비용이 큰 경우에 유사하거나 동일한 객체가 이미 존재한다면 객체 생성에
> 필요한 비용을 줄이기 위해 원본 객체를 복사하여 새로운 객체를 생성하는 패턴입니다.
> 객체의 복잡한 생성 과정을 캡슐화하여 감추고 추상화된 리턴 타입을 사용할 수 있습니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/prototype.png?raw=true"/>
</p>

### 구현 방법

```java
public class Object {
    
    // ...생략
    
    @HotSpotIntrinsicCandidate
    protected native Object clone() throws CloneNotSupportedException;

    // ...생략
    
}
```

자바에서 기본 복제 메커니즘으로 제공하는 Object 클래스의 네이티브 메소드인 clone 메소드를 사용합니다.
해당 메소드를 사용하기 위해서는 다음 조건을 만족해야합니다.
- Cloneable 인터페이스를 구현한다.
- object.clone() != object
- object.clone().getClass () == object.getClass()
- object.clone().equals(object)

```java
public class GithubIssue implements Cloneable {

    private final Long id;

    private final GithubRepository githubRepository;

    private String title;

    public GithubIssue(Long id, GithubRepository githubRepository) {
        this.id = id;
        this.githubRepository = githubRepository;
    }

    public Long getId() {
        return id;
    }

    public GithubRepository getGithubRepository() {
        return githubRepository;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GithubIssue that = (GithubIssue) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(githubRepository, that.githubRepository) &&
                Objects.equals(title, that.title);
    }

}
```

프로토타입을 적용할 클래스를 정의합니다. Cloneable 인터페이스를 구현하고 인스턴스 필드 값을 비교하도록 equals 메소드를 오버라이딩합니다.

```java
@Test
public void 자바에서_제공하는_기본복제메커니즘_clone을_사용하여_구현한다() throws CloneNotSupportedException {
    // given
    GithubRepository githubRepository = new GithubRepository();
    githubRepository.setUser("sinbom");
    githubRepository.setName("design-pattern");
    GithubIssue githubIssue = new GithubIssue(1L, githubRepository);
    githubIssue.setTitle("builder pattern");

    // when
    Object clonedIssue = githubIssue.clone();

    // then
    assertTrue(githubIssue instanceof Cloneable);
    assertNotSame(githubIssue, clonedIssue);
    assertSame(githubIssue.getClass(), clonedIssue.getClass());
    assertEquals(githubIssue, clonedIssue);
}
```

clone 메소드를 통해 프로토타입 패턴을 구현하여 복사된 객체는 위의 조건을 모두 만족합니다.

```java
@Test
public void 자바에서_제공하는_기본복제메커니즘_clone은_shallow_copy이다() throws CloneNotSupportedException {
    // given
    GithubRepository githubRepository = new GithubRepository();
    githubRepository.setUser("sinbom");
    githubRepository.setName("design-pattern");
    GithubIssue githubIssue = new GithubIssue(1L, githubRepository);
    githubIssue.setTitle("builder pattern");

    // when
    GithubIssue clonedIssue = (GithubIssue) githubIssue.clone();
    GithubRepository cloneIssuesRepository = clonedIssue.getGithubRepository();
    cloneIssuesRepository.setUser("YoungjinSin");
    cloneIssuesRepository.setName("JAVA_DEIGN_PATTERN");

    // then
    assertNotSame(githubIssue, clonedIssue);
    assertSame(githubIssue.getClass(), clonedIssue.getClass());
    assertEquals(githubIssue, clonedIssue);
    assertEquals(githubRepository, cloneIssuesRepository);
    assertSame(githubRepository, cloneIssuesRepository); // shallow copy
}

```

하지만 기본으로 제공되는 clone 메소드는 shallow copy이기 때문에 복사된 객체의 인스터스 필드가
primitive type 또는 immutable object type이 아니라면 해당 객체의 변경사항이 참조하는 모든 객체에 
영향을 미치므로 deep copy를 구현할 필요가 있습니다.

```java
public class GithubIssue implements Cloneable {

    // ...생략
    
    @Override
    public Object clone() {
        GithubRepository githubRepository = new GithubRepository();
        githubRepository.setUser(this.githubRepository.getUser());
        githubRepository.setName(this.githubRepository.getName());

        GithubIssue githubIssue = new GithubIssue(this.id, githubRepository);
        githubIssue.setTitle(this.title);

        return githubIssue;
    }

    // ...생략
}
```

```java
@Test
public void 자바에서_제공하는_기본복제메커니즘_clone을_deep_copy로_구현한다() throws CloneNotSupportedException {
    // given
    GithubRepository githubRepository = new GithubRepository();
    githubRepository.setUser("sinbom");
    githubRepository.setName("design-pattern");
    GithubIssue githubIssue = new GithubIssue(1L, githubRepository);
    githubIssue.setTitle("builder pattern");

    // when
    GithubIssue clonedIssue = (GithubIssue) githubIssue.clone();
    GithubRepository cloneIssuesRepository = clonedIssue.getGithubRepository();
    cloneIssuesRepository.setUser("YoungjinSin");
    cloneIssuesRepository.setName("JAVA_DEIGN_PATTERN");

    // then
    assertNotSame(githubIssue, clonedIssue);
    assertSame(githubIssue.getClass(), clonedIssue.getClass());
    assertNotEquals(githubIssue, clonedIssue);
    assertNotSame(githubRepository, cloneIssuesRepository);
    assertNotEquals(githubRepository, cloneIssuesRepository);
}
```

원본 객체와 복제 객체가 동일한 레퍼런스를 참조하지 않도록 deep copy를 구현합니다. 객체 레퍼런스 참조가
깊거나 순환 참조가 형성되어 있는 경우 구현이 복잡할 수 있습니다.
