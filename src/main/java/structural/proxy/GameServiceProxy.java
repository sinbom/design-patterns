package structural.proxy;

public class GameServiceProxy implements GameService {

    private final GameService gameService;

    public GameServiceProxy(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void startGame() {
        System.out.println("프록시님 등장!");
        long start = System.currentTimeMillis();

        this.gameService.startGame();

        System.out.println(System.currentTimeMillis() - start);
    }

}
