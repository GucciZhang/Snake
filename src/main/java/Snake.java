import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Snake {

    private Group snake;
    private int speed;
    private int moveX;
    private int moveY;

    private ArrayList<Shape> nodes;

    //private final Color headColor = Color.MIDNIGHTBLUE;
    private final Color snakeColor = Color.DARKGREEN;

    public Snake(int speed) {
        snake = new Group();
        this.speed = speed;
        this.moveX = 1;
        this.moveY = 0;
        nodes = new ArrayList<>();

        Polygon head = new Polygon(0, 0, 20, 5, 30, 15, 20, 25, 0, 30, 5, 15);
        //head.setFill(headColor);
        Image snakeHead = new Image("snake_head_good.jpg");
        head.setFill(new ImagePattern(snakeHead));
        head.setTranslateX(90);
        head.setTranslateY(90);
        nodes.add(head);
        snake.getChildren().add(head);
    }

    public Snake(int speed, Snake snake) {
        this.snake = snake.getSnake();
        this.speed = speed;
        this.moveX = snake.getMoveX();
        this.moveY = snake.getMoveY();
        this.nodes = snake.getNodes();
    }

    public void move() {
        for (int i = nodes.size() - 1; i >= 1; --i) {
            Shape cur = nodes.get(i);
            Shape next = nodes.get(i-1);

            double curX = cur.getTranslateX();
            double curY = cur.getTranslateY();
            double nextX = next.getTranslateX();
            double nextY = next.getTranslateY();
            double xDiff = Math.signum(nextX - curX);
            double yDiff = Math.signum(nextY - curY);

            if (curX % 30 == 0 && curY % 30 == 0) {
                cur.setTranslateX(curX + xDiff * speed);
                cur.setTranslateY(curY + yDiff * speed);
            } else if (curX % 30 == 0) {
                cur.setTranslateY(curY + yDiff * speed);
            } else {
                cur.setTranslateX(curX + xDiff * speed);
            }
        }
        Shape head = nodes.get(0);
        head.setTranslateX(head.getTranslateX() + moveX * speed);
        head.setTranslateY(head.getTranslateY() + moveY * speed);
    }

    public void turnLeft() {
        if (moveX == 1) {
            moveX = 0;
            moveY = -1;
        } else if (moveX == -1) {
            moveX = 0;
            moveY = 1;
        } else if (moveY == 1) {
            moveX = 1;
            moveY = 0;
        } else {
            moveX = -1;
            moveY = 0;
        }

        Shape head = nodes.get(0);
        head.setRotate((head.getRotate() - 90) % 360);
    }

    public void turnRight() {
        if (moveX == 1) {
            moveX = 0;
            moveY = 1;
        } else if (moveX == -1) {
            moveX = 0;
            moveY = -1;
        } else if (moveY == 1) {
            moveX = -1;
            moveY = 0;
        } else {
            moveX = 1;
            moveY = 0;
        }

        Shape head = nodes.get(0);
        head.setRotate((head.getRotate() + 90) % 360);
    }

    public void grow() {
        Rectangle newTail = new Rectangle(30, 30);
        newTail.setFill(snakeColor);
        newTail.setArcHeight(25);
        newTail.setArcWidth(25);

        Shape tail = nodes.get(nodes.size() - 1);
        if (tail.getTranslateX() % 30 == 0) {
            newTail.setTranslateX(tail.getTranslateX());
            if (tail.getTranslateY() % 30 == 30 - speed) {
                newTail.setTranslateY(tail.getTranslateY() + 30);
            } else {
                newTail.setTranslateY(tail.getTranslateY() - 30);
            }
        } else {
            newTail.setTranslateY(tail.getTranslateY());
            if (tail.getTranslateX() % 30 == 30 - speed) {
                newTail.setTranslateX(tail.getTranslateX() + 30);
            } else {
                newTail.setTranslateX(tail.getTranslateX() - 30);
            }
        }

        snake.getChildren().add(newTail);
        nodes.add(newTail);
    }

    public Group getSnake() {
        return snake;
    }

    public ArrayList<Shape> getNodes() {
        return nodes;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMoveX() {
       return moveX;
    }

    public int getMoveY() {
        return moveY;
    }
}
