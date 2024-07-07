import gameplay.Game;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - The Ass5Game class. Creates a game and plays it.
 */
public class Ass5Game {

    /**
     * Creates a game and plays it.
     * @param args - Command-line arguments. This variable is not used in this program.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
