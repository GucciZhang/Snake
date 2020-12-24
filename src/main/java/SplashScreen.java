import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;

public class SplashScreen {
    private final int SCREEN_WIDTH = 1280;
    private final int SCREEN_HEIGHT = 800;

    private Scene splashScreen;

    public SplashScreen() {
        StackPane group = new StackPane();

        // Background snake image
        try {
            Image img = new Image("snake.jpg");
            BackgroundImage background = new BackgroundImage(img,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            group.setBackground(new Background(background));
        } catch (Exception ex) {
            System.out.println("Splashscreen background image failed");
        }

        // Title
        Text title = new Text("Snake");
        title.setFont(Font.font ("Algerian", 300));
        title.setFill(Color.RED);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        group.getChildren().add(title);

        // Personal info
        Text info = new Text("Jeff Zhang\n20772204");
        info.setFont(Font.font("Baskerville Old Face", 30));
        info.setFill(Color.WHITE);
        StackPane.setAlignment(info, Pos.BOTTOM_LEFT);
        group.getChildren().add(info);

        // Instructions
        Text instr = new Text("Use left & right arrow keys to turn the snake\n" +
                "Don't run into the wall or yourself\n" +
                "When you eat fruit, you will grow longer\n" +
                "Eat fruit and survive to progress and earn points\n" +
                "Press SPACEBAR to start");
        instr.setFont(Font.font("Trebuchet MS", 40));
        instr.setFill(Color.WHITE);
        instr.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(instr, Pos.BOTTOM_CENTER);
        group.getChildren().add(instr);

        splashScreen = new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public Scene getSplashScreen() {
        return splashScreen;
    }
}
