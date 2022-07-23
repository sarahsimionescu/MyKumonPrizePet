package mykumonprizepet;

/**
 *
 * @author sarah simionescu
 */
public class MyKumonPrizePet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Display display = new Display();
        Game game = new Game();
        display.G = game;
        game.D = display;
        display.start();
    }
    
}
