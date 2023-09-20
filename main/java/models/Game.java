package main.java.models;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Random;

import main.java.enums.Direction;

public class Game extends JPanel implements ActionListener{

    private Random random = new Random();
    private Timer timer;

    private final int DELAY = 200;

    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private final int UNIT_SIZE = 20;
    private final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    
    private final int snakeX = WIDTH/2 ;
    private final int snakeY = HEIGHT/2 ;
    private int foodX;
    private int foodY;

    private final int imageWidth = 320;
    private final int imageHeight = 256;
    private final int subimageWidth = 64;
    private final int subimageHeigth = 64;
    private BufferedImage snakeImage;


    private Direction direction = Direction.RIGHT;
    private boolean isGameOver = false;

    private Snake snake ;

    public Game() {

        try {
            
            snakeImage = ImageIO.read(getClass().getResource("snake-graphics.png"));


        } catch (IOException e) {
            
            e.printStackTrace();

        }
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.ORANGE);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();

    }

    private void startGame() {

        snake = new Snake(snakeX, snakeY);
        generateFood();
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public boolean checkCollision() {
        return snake.checkSelfColision();
    }

    public void gameOver() {
        System.exit(0);
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            } else if (e.getKeyCode() == KeyEvent.VK_UP && direction != Direction.DOWN) {
                direction = Direction.UP;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != Direction.LEFT){
                direction = Direction.RIGHT;
            }
        }
    }

    public boolean move() {
        return snake.move(direction, UNIT_SIZE, new Point(foodX, foodY), WIDTH, HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        paintComponent(getGraphics());
        if(move())
            generateFood();
        if(checkCollision())
            gameOver();

       
    }

    public void generateFood(){
        int maxX = (WIDTH / UNIT_SIZE) - 1; // Calculate the maximum X index
        int maxY = (HEIGHT / UNIT_SIZE) - 1; // Calculate the maximum Y index

        // Generate random X and Y coordinates within the boundaries
        foodX = random.nextInt(maxX) * UNIT_SIZE;
        foodY = random.nextInt(maxY) * UNIT_SIZE;
    }   

    @Override
    public void paintComponent(Graphics g) {
    super.paintComponent(g);

    BufferedImage head = snakeImage.getSubimage(256, 0, subimageWidth, subimageHeigth);

    g.setColor(Color.GREEN); 
    for (Point segment : snake.getSnakeBody()) {

        //g.fillRect(segment.x, segment.y, UNIT_SIZE, UNIT_SIZE);
        g.drawImage(head, segment.x, segment.y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());
        
    
    }

    g.setColor(Color.RED); 
    g.fillRect(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

    // Add other elements like score, game-over screen, etc.
}


}