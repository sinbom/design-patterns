package creational.prototype;

import java.util.Objects;

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
    public Object clone() {
        GithubRepository githubRepository = new GithubRepository();
        githubRepository.setUser(this.githubRepository.getUser());
        githubRepository.setName(this.githubRepository.getName());

        GithubIssue githubIssue = new GithubIssue(this.id, githubRepository);
        githubIssue.setTitle(this.title);

        return githubIssue;
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
