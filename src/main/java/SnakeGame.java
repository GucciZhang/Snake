import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class SnakeGame extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = new SceneManager(stage);

        // Splash Screen
        Scene splashScreen = sceneManager.getSplashScreen();

        stage.setTitle("Snake: Splashscreen");
        stage.setScene(splashScreen);
        stage.show();
    }
}
