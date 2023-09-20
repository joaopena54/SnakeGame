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

    private BufferedImage bufferImage;

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

    if (bufferImage == null || bufferImage.getWidth() != getWidth() || bufferImage.getHeight() != getHeight()) {
        bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    Graphics bufferGraphics = bufferImage.getGraphics();
    bufferGraphics.setColor(Color.BLACK);
    bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

    BufferedImage headUp = snakeImage.getSubimage(192, 0, subimageWidth, subimageHeigth);
    BufferedImage headLeft = snakeImage.getSubimage(192, 64, subimageWidth, subimageHeigth);
    BufferedImage headRight = snakeImage.getSubimage(256, 0, subimageWidth, subimageHeigth);
    BufferedImage headDown = snakeImage.getSubimage(256, 64, subimageWidth, subimageHeigth);
    BufferedImage bodyHorizontal = snakeImage.getSubimage(64, 0, subimageWidth, subimageHeigth);
    BufferedImage bodyVertical = snakeImage.getSubimage(128, 64, subimageWidth, subimageHeigth);
    BufferedImage tailUp = snakeImage.getSubimage(256, 192, subimageWidth, subimageHeigth);
    BufferedImage tailLeft = snakeImage.getSubimage(256, 128, subimageWidth, subimageHeigth);
    BufferedImage tailRight = snakeImage.getSubimage(192, 192, subimageWidth, subimageHeigth);
    BufferedImage tailDown = snakeImage.getSubimage(192, 128, subimageWidth, subimageHeigth);
    BufferedImage turnRightUpDownLeft = snakeImage.getSubimage(128, 128, subimageWidth, subimageHeigth);
    BufferedImage turnUpLeftRightDown = snakeImage.getSubimage(128, 0, subimageWidth, subimageHeigth);
    BufferedImage turnUpRightLeftDown = snakeImage.getSubimage(0, 0, subimageWidth, subimageHeigth);
    BufferedImage turnDownRightLeftUP = snakeImage.getSubimage(0, 64, subimageWidth, subimageHeigth);
    BufferedImage apple = snakeImage.getSubimage(0, 192, subimageWidth, subimageHeigth);

    g.setColor(Color.GREEN); 
    
    for (int i = 0; i < snake.getSize(); i++) {

        if(i == 0){

            if(snake.getPart(i).getSecond() == 'r'){

                g.drawImage(headRight, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

            } else if (snake.getPart(i).getSecond() == 'u') {

                g.drawImage(headUp, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

            } else if (snake.getPart(i).getSecond() == 'd') {
            
                g.drawImage(headDown, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

            } else {

                g.drawImage(headLeft, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

            }

            
        } else if(i == (snake.getSize()-1)){

            if(snake.getPart(i).getSecond() == 'r'){

                g.drawImage(tailLeft, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

            } else if (snake.getPart(i).getSecond() == 'u') {

                g.drawImage(tailDown, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

            } else if (snake.getPart(i).getSecond() == 'd') {
            
                g.drawImage(tailUp, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

            } else {

                g.drawImage(tailRight, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

            }

        } else {

            if(snake.getPart(i).getSecond() == 'r'){

                if(snake.getPart(i-1).getSecond() == 'u'){

                    g.drawImage(turnRightUpDownLeft, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

                } else if (snake.getPart(i-1).getSecond() == 'd') {

                    g.drawImage(turnUpLeftRightDown, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());
                
                } else {

                    g.drawImage(bodyHorizontal, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

                }

            } else if (snake.getPart(i).getSecond() == 'u') {

                if(snake.getPart(i-1).getSecond() == 'r'){

                    g.drawImage(turnUpRightLeftDown, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

                } else if (snake.getPart(i-1).getSecond() == 'l') {

                    g.drawImage(turnUpLeftRightDown, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());
                
                } else {

                    g.drawImage(bodyVertical, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

                }

            } else if (snake.getPart(i).getSecond() == 'd') {
            
                if(snake.getPart(i-1).getSecond() == 'r'){

                    g.drawImage(turnDownRightLeftUP, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

                } else if (snake.getPart(i-1).getSecond() == 'l') {

                    g.drawImage(turnRightUpDownLeft, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());
                
                } else {

                    g.drawImage(bodyVertical, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

                }

            } else {

                if(snake.getPart(i-1).getSecond() == 'u'){

                    g.drawImage(turnDownRightLeftUP, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

                } else if (snake.getPart(i-1).getSecond() == 'd') {

                    g.drawImage(turnUpRightLeftDown, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());
                
                } else {

                    g.drawImage(bodyHorizontal, snake.getPart(i).getFirst().x, snake.getPart(i).getFirst().y, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

                }
            }   

        }

    
    }

    g.setColor(Color.RED); 
    g.drawImage(apple, foodX, foodY, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());

    // Add other elements like score, game-over screen, etc.
}


}