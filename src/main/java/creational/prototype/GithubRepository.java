package creational.prototype;

import java.util.Objects;

public class GithubRepository {

    private String name;

    private String user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GithubRepository that = (GithubRepository) o;

        return Objects.equals(name, that.name) && Objects.equals(user, that.user);
    }

}
