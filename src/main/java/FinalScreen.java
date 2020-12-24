import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class FinalScreen {
    private final int SCREEN_WIDTH = 1280;
    private final int SCREEN_HEIGHT = 800;

    Scene finalScreen;
    ArrayList<Integer> highScores = new ArrayList<>();
    Text info;

    public FinalScreen() {
        StackPane group = new StackPane();

        // Background colour
        group.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, null, null)));

        // Title
        Text title = new Text("Game Over\nHighScores");
        title.setFont(Font.font ("Trebuchet MS", 90));
        title.setFill(Color.WHITE);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        group.getChildren().add(title);

        // List
        StringBuilder list = new StringBuilder("");
        for (int i = 0; i < 10; ++i) {
            if (i < highScores.size()) {
                list.append(String.format("%d. %d\n", i+1, highScores.get(i)));
            } else {
                list.append(String.format("%d.\n", i+1));
            }
        }
        info = new Text(list.toString());
        info.setFont(Font.font("Trebuchet MS", 30));
        info.setFill(Color.WHITE);
        StackPane.setAlignment(info, Pos.CENTER);
        group.getChildren().add(info);

        // Instructions
        Text instr = new Text("Press R to return to Splash Screen");
        instr.setFont(Font.font("Trebuchet MS", 40));
        instr.setFill(Color.WHITE);
        instr.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(instr, Pos.BOTTOM_CENTER);
        group.getChildren().add(instr);

        finalScreen = new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public Scene getFinalScreen() {
        return finalScreen;
    }

    public void addScore(int score) {
        if (highScores.size() == 10) {
            if (score > highScores.get(9)) {
                highScores.remove(9);
                highScores.add(score);
            }
        } else {
            highScores.add(score);
        }
        Collections.sort(highScores, Collections.reverseOrder());

        StringBuilder list = new StringBuilder("");
        for (int i = 0; i < 10; ++i) {
            if (i < highScores.size()) {
                list.append(String.format("%d. %d\n", i+1, highScores.get(i)));
            } else {
                list.append(String.format("%d.\n", i+1));
            }
        }
        info.setText(list.toString());
    }
}
