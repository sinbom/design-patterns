package structural.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
