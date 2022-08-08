package structural.adapter;

import org.junit.jupiter.api.Test;
import structural.adapter.*;

import static org.junit.jupiter.api.Assertions.assertSame;

public class AdapterTest {

    @Test
    public void 다른_타입의_인터페이스를_어댑터를_통해_사용한다() {
        // given & when
        AccountService accountService = new AccountService();
        UserDetailsService accountUserDetailsService = new AccountUserDetailsService(accountService);
        UserDetails userDetails = accountUserDetailsService.loadUser("sinbom");

        // then
        assertSame(accountUserDetailsService.getClass(), AccountUserDetailsService.class);
        assertSame(userDetails.getClass(), AccountUserDetails.class);
    }

}
