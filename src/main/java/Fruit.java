import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;

public class Fruit {
    Group fruits = new Group();
    int numFruits;

    final Image orange = new Image("orange.jpg");

    public Fruit(int numFruits, Snake snake) {
        this.numFruits = numFruits;

        for (int i = 1; i <= 5; ++i) {
            for(int j = 1; j <= numFruits/5; ++j) {
                boolean work = true;
                for (Shape node: snake.getNodes()) {
                    Bounds sBound = node.getBoundsInParent();
                    if (sBound.contains(i*5*30+15, j*5*30+15)) {
                        work = false;
                        break;
                    }
                }
                if (work) {
                    Circle fruit = new Circle(15);
                    fruit.setTranslateX(i*5*30+15);
                    fruit.setTranslateY(j*5*30+15);
                    //fruit.setFill(Color.RED);
                    fruit.setFill(new ImagePattern(orange));
                    fruits.getChildren().add(fruit);
                } else {
                    generateFruit(snake.getNodes());
                }
            }
        }
    }

    public Group getFruits() {
        return fruits;
    }

    public void generateFruit(ArrayList<Shape> snake) {
        Random rand = new Random();
        while (true) {
            int x = rand.nextInt(30) * 30;
            int y = rand.nextInt(20) * 30;

            boolean work = true;
            for (Shape node: snake) {
                Bounds sBound = node.getBoundsInParent();
                if (sBound.intersects(x, y, 30, 30)) {
                    work = false;
                    break;
                }
//                if ((int) node.getTranslateX() / 30 == x &&
//                        (int) node.getTranslateY() / 30 == y) {
//                    work = false;
//                    break;
//                }
            }

            if (work) {
                for (Node existFruit : fruits.getChildren()) {
                    Bounds existFBound = existFruit.getBoundsInParent();
                    if (existFBound.intersects(x, y, 30, 30)) {
                        work = false;
                        break;
                    }
                }
            } else {
                continue;
            }

            if (work) {
                Circle fruit = new Circle(15);
                fruit.setTranslateX(x+15);
                fruit.setTranslateY(y+15);
                //fruit.setFill(Color.RED);
                fruit.setFill(new ImagePattern(orange));
                fruits.getChildren().add(fruit);
                break;
            }
        }
    }
}
