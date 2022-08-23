# Structural Patterns(구조 관련 패턴)

## Decorator (데코레이터 패턴)
> 주어진 상황 및 용도에 따라 어떤 객체에 책임을 덧붙이는 패턴으로, 기능 확장이 필요할 때 상속 대신 위임을 사용하여
> 기존 코드를 변경하지 않고 유연하게 런타임에 동적으로 객체의 기능을 확장하는 패턴입니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/decorator.png?raw=true"/>
</p>

### 구현 방법

```java
public interface CommentService {

    void addComment(String comment);

}
```

```java
public class DefaultCommentService implements CommentService {

    @Override
    public void addComment(String comment) {
        System.out.println(comment);
    }

}
```

컴포넌트 인터페이스와 구현체를 정의합니다.

```java
public class CommentDecorator implements CommentService{

    private final CommentService commentService;

    public CommentDecorator(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void addComment(String comment) {
        commentService.addComment(comment);
    }

}
```

```java
public class TrimmingCommentDecorator extends CommentDecorator {

    public TrimmingCommentDecorator(CommentService commentService) {
        super(commentService);
    }

    @Override
    public void addComment(String comment) {
        super.addComment(trim(comment));
    }

    private String trim(String comment) {
        return comment.replace("...", "");
    }

}
```

```java
public class SpamFilteringCommentDecorator extends CommentDecorator {

    public SpamFilteringCommentDecorator(CommentService commentService) {
        super(commentService);
    }

    @Override
    public void addComment(String comment) {
        if (isNotSpam(comment)) {
            super.addComment(comment);
        }
    }

    private boolean isNotSpam(String comment) {
        return !comment.contains("https");
    }

}
```

컴포넌트 인터페이스를 구현하는 데코레이터 클래스를 정의합니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/decorator-diagram.png?raw=true"/>
</p>

인터페이스와 구현체들을 정의한 다이어그램은 다음과 같습니다.

```java
class DecoratorTest {

    private ByteArrayOutputStream byteArrayOutputStream;

    private PrintStream printStream;

    @BeforeEach
    void beforeEach() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
    }

    @AfterEach
    void afterEach() throws IOException {
        printStream.close();
        byteArrayOutputStream.close();
        System.setOut(System.out);
    }

    @Test
    void 런타임에_동적으로_객체의_기능을_확장한다() {
        // given
        boolean enabledSpamFilter = true;
        boolean enabledTrimming = true;
        String[] comments = {"안녕하세요", "신영진입니다.", "https://github.com/sinbom"};
        CommentService commentService = new DefaultCommentService();

        if (enabledSpamFilter) {
            commentService = new SpamFilteringCommentDecorator(commentService);
        }

        if (enabledTrimming) {
            commentService = new TrimmingCommentDecorator(commentService);
        }

        // when
        commentService.addComment(comments[0]);
        commentService.addComment(comments[1]);
        commentService.addComment(comments[2]);

        // then
        assertEquals(String.join("\n", comments[0], comments[1]) + "\n",
            byteArrayOutputStream.toString());
    }

}
```

새로운 기능이 추가되더라도 기존의 코드를 변경하지 않고, 기존의 기능을 조합하여 컴파일 타임이 아닌 런타임에 동적으로 기능을 추가 및 변경할 수 있습니다.
하지만 데코레이터를 조합하는 코드가 복잡해 질 수 있는 단점이 있습니다.
