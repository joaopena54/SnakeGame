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

    public boolean move(Direction direction, int unitSize,Point fruit, int WIDTH, int HEIGHT){

        if(direction == Direction.UP) {

            if( (int)body.getFirst().getY() - unitSize < 0 ){
                body.addFirst(new Point( (int) body.getFirst().getX() , HEIGHT));
            } else {
                body.addFirst(new Point( (int) body.getFirst().getX() , (int)body.getFirst().getY() - unitSize));
            }  

        } else if(direction == Direction.LEFT) {

            if((int) body.getFirst().getX() - unitSize < 0){
                body.addFirst(new Point( WIDTH, (int)body.getFirst().getY()));
            } else {
                body.addFirst(new Point( (int) body.getFirst().getX() - unitSize, (int)body.getFirst().getY()));
            }
            
        } else if(direction == Direction.RIGHT) {

            if((int) body.getFirst().getX() + unitSize >= WIDTH){
                body.addFirst(new Point( 0 , (int)body.getFirst().getY()));
            } else{
                body.addFirst(new Point( (int) body.getFirst().getX() + unitSize , (int)body.getFirst().getY()));
            }

        } else {

            if((int)body.getFirst().getY() + unitSize >= HEIGHT ){
                body.addFirst(new Point( (int) body.getFirst().getX() , 0));
            } else {
                body.addFirst(new Point( (int) body.getFirst().getX() , (int)body.getFirst().getY() + unitSize));
            }

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
        
       if (body.size() > 3) {

        for(int i = 1 ; i < body.size(); i++){
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

