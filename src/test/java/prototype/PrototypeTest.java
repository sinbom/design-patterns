package prototype;

import creational.prototype.GithubIssue;
import creational.prototype.GithubRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrototypeTest {

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
        assertSame(githubRepository, cloneIssuesRepository);
        assertEquals(githubRepository, cloneIssuesRepository);
    }

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

}
