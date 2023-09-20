package main.java.models;

import java.awt.Point;
import java.util.LinkedList;

import main.java.enums.Direction;

public class Snake {
    
    private LinkedList<SnakePart> body;

    public Snake(int x , int y){

        body = new LinkedList<SnakePart>();
        SnakePart start = new SnakePart( new Point(x, y) , 'r');
        body.add(start);

    }

    public boolean move(Direction direction, int unitSize,Point fruit, int WIDTH, int HEIGHT){

        if(direction == Direction.UP) {

            if( (int)body.getFirst().getFirst().getY() - unitSize < 0 ){
                body.addFirst(new SnakePart(new Point( (int) body.getFirst().getFirst().getX() , HEIGHT) , 'u'));
            } else {
                body.addFirst(new SnakePart( new Point( (int) body.getFirst().getFirst().getX() , (int)body.getFirst().getFirst().getY() - unitSize) , 'u'));
            }  

        } else if(direction == Direction.LEFT) {

            if((int) body.getFirst().getFirst().getX() - unitSize < 0){
                body.addFirst(new SnakePart(new Point( WIDTH, (int)body.getFirst().getFirst().getY()) , 'l'));
            } else {
                body.addFirst(new SnakePart(new Point( (int) body.getFirst().getFirst().getX() - unitSize, (int)body.getFirst().getFirst().getY() ), 'l'));
            }
            
        } else if(direction == Direction.RIGHT) {

            if((int) body.getFirst().getFirst().getX() + unitSize >= WIDTH){
                body.addFirst(new SnakePart(new Point( 0 , (int)body.getFirst().getFirst().getY()),'r'));
            } else{
                body.addFirst(new SnakePart(new Point( (int) body.getFirst().getFirst().getX() + unitSize , (int)body.getFirst().getFirst().getY()), 'r'));
            }

        } else {

            if((int)body.getFirst().getFirst().getY() + unitSize >= HEIGHT ){
                body.addFirst(new SnakePart(new Point( (int) body.getFirst().getFirst().getX() , 0), 'd'));
            } else {
                body.addFirst(new SnakePart(new Point( (int) body.getFirst().getFirst().getX() , (int)body.getFirst().getFirst().getY() + unitSize), 'd'));
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
        return body.getFirst().getFirst().equals(fruit);
    }

    public boolean checkSelfColision(){
        
       if (body.size() > 3) {

        for(int i = 1 ; i < body.size(); i++){
            if(body.get(i).equals(body.getFirst().getFirst())){
                return true;
            }
        }

       }

    return false;
        
    }

    public LinkedList<SnakePart> getSnakeBody() {
        
        return body;

    }


    public int getSize(){

        return body.size();

    }


    public SnakePart getPart(int i) {

        return body.get(i);

    }
}

