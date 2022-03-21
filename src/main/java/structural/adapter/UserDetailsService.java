package structural.adapter;

public interface UserDetailsService {

    UserDetails loadUser(String username);

}
