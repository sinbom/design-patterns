package structural.adapter;

public class AccountService {

    public Account findByUsername(String username) {
        return new Account(username, "");
    }

}
