package main.java.models;

import java.awt.Point;

public class SnakePart {

        private Point point;
        private char direction;
    
        public SnakePart(Point first, char second) {
            this.point = first;
            this.direction = second;
        }
    
        public Point getFirst() {
            return point;
        }
    
        public char getSecond() {
            return direction;
        }

        public void setFirst(Point point){
            this.point = point;
        }

        public void setSecond(char direction){
            this.direction = direction;
        }
    }
    

