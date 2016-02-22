import javax.swing.*;

/**
 * Created by AZbest on 22.02.2016.
 */
public class Game {
    private JPanel mainPane;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setContentPane(new Game().mainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
