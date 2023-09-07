package main.java;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.java.models.Game;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Game");
            Game game = new Game();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    
}
