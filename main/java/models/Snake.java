package main.java.models;

import java.awt.Point;
import java.util.LinkedList;

import main.java.enums.Direction;

public class Snake {
    
    private LinkedList<Point> body;

    public Snake(int x , int y){

        body = new LinkedList<Point>();
        Point start = new Point(x, y);
        body.add(start);

    }

    public boolean move(Direction direction, int unitSize,Point fruit){

        if(direction == Direction.UP) {

            body.addFirst(new Point( (int) body.getFirst().getX() , (int)body.getFirst().getY() - unitSize));

        } else if(direction == Direction.LEFT) {

            body.addFirst(new Point( (int) body.getFirst().getX() - unitSize, (int)body.getFirst().getY()));

        } else if(direction == Direction.RIGHT) {

            body.addFirst(new Point( (int) body.getFirst().getX() + unitSize , (int)body.getFirst().getY()));

        } else {

            body.addFirst(new Point( (int) body.getFirst().getX() , (int)body.getFirst().getY() + unitSize));

        }

        if(!CheckFruitCollision(fruit)){
            body.remove(body.getLast());
            return false;
        } else {
            return true;
        }   
            


    }

    private boolean CheckFruitCollision(Point fruit) {
        return body.getFirst().equals(fruit);
    }

    public boolean checkSelfColision(){
        
       if (body.size() > 4) {

        for(int i = 4 ; i < body.size(); i++){
            if(body.get(i).equals(body.getFirst())){
                return true;
            }
        }

       }

    return false;
        
    }

    public LinkedList<Point> getSnakeBody() {
        
        return body;

    }
}
