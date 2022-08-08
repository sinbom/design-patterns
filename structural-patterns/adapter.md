# Structural Patterns(구조 관련 패턴)

## Adapter (어댑터 패턴)
> 다른 인터페이스를 사용하고자하는 호환성이 없는 인터페이스와 함꼐 동작할 수 있도록
> 구현체에서 실제 사용하는 다른 인터페이스 타입의 객체를 위임하여 사용하는 패턴입니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/adapter.jpg?raw=true"/>
</p>

### 구현 방법

```java
public interface UserDetailsService {

    UserDetails loadUser(String username);

}
```

```java
public interface UserDetails {

    String getUsername();

    String getPassword();

}
```

다음과 같은 인터페이스들이 주어져있습니다.

```java
public class Account {

    private final String name;

    private final String password;

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
```

```java
public class AccountService {

    public Account findByUsername(String username) {
        // 사용자 DB 조회 로직
        return new Account(username, "");
    }

}
```

유저 도메인 클래스와 유저 서비스 클래스(Adaptee)가 있습니다.

```java
public class AccountUserDetails implements UserDetails {

    private final Account account;

    public AccountUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public String getUsername() {
        return account.getName();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

}
```

```java
public class AccountUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    public AccountUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUser(String username) {
        Account account = accountService.findByUsername(username);

        return new AccountUserDetails(account);
    }

}
```

유저 도메인 클래스와 유저 서비스 클래스의 소스 코드를 수정하지 않고 인터페이스 구현체인 클래스(Adapter)를 생성하고
객체(Adaptee)를 위임하여 사용합니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/adapter-diagram.png?raw=true"/>
</p>

인터페이스와 구현체들을 정의한 다이어그램은 다음과 같습니다.

```java
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
```

다른 타입의 인터페이스를 어댑터를 통해 사용할 수 있습니다. 개발자가 직접 개발한 클래스는 인터페이스를 직접 구현하도록 수정할 수 있지만
수정할 수 없는 외부 서드파티 라이브러리의 경우 어댑터 패턴을 통해 호환되도록 구현할 수 있습니다.
