import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class SceneManager {
    private final int SCREEN_WIDTH = 1280;
    private final int SCREEN_HEIGHT = 800;
    // Permanent scenes
    private SplashScreen splashScreen;
    private FinalScreen finalScreen;

    private Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
        initSplashScreen();
        initFinalScreen();
    }

    private void initSplashScreen() {
        splashScreen = new SplashScreen();
        splashScreen.getSplashScreen().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                stage.setTitle("Snake: Level One");
                Level levelOne = getLevelOne(0, null);
                stage.setScene(levelOne.getScene());
                levelOne.play();
            } else if (keyEvent.getCode() == KeyCode.DIGIT1) {
                stage.setTitle("Snake: Level One");
                Level levelOne = getLevelOne(0, null);
                stage.setScene(levelOne.getScene());
                levelOne.play();
            } else if (keyEvent.getCode() == KeyCode.DIGIT2) {
                stage.setTitle("Snake: Level Two");
                Level levelTwo = getLevelTwo(0, null);
                stage.setScene(levelTwo.getScene());
                levelTwo.play();
            } else if (keyEvent.getCode() == KeyCode.DIGIT3) {
                stage.setTitle("Snake: Level Three");
                Level levelThree = getLevelThree(0, null);
                stage.setScene(levelThree.getScene());
                levelThree.play();
            } else if (keyEvent.getCode() == KeyCode.Q) {
                stage.setTitle("Snake: High Scores");
                stage.setScene(finalScreen.getFinalScreen());
            }
        });
    };

    private void initFinalScreen() {
        finalScreen = new FinalScreen();
        finalScreen.getFinalScreen().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.R) {
                stage.setTitle("Snake: Splashscreen");
                stage.setScene(splashScreen.getSplashScreen());
            }
        });
    }

    public Scene getSplashScreen() {
        return splashScreen.getSplashScreen();
    }

    public Level getLevelOne(int score, Snake snake) {
        Level levelOne = new Level(1, score, snake);
        levelOne.getScene().addEventHandler(Level.GoLevel2.GO_LEVEL_2,
                event -> {
                    stage.setTitle("Snake: Level Two");
                    Level levelTwo = getLevelTwo(event.getScore(), event.getSnake());
                    stage.setScene(levelTwo.getScene());
                    levelTwo.play();
                });
        levelOne.getScene().addEventHandler(Level.GoLevel3.GO_LEVEL_3,
                event -> {
                    stage.setTitle("Snake: Level Three");
                    Level levelThree = getLevelThree(event.getScore(), event.getSnake());
                    stage.setScene(levelThree.getScene());
                    levelThree.play();
                });
        levelOne.getScene().addEventHandler(Level.HighScore.HIGH_SCORE,
                event -> {
                    stage.setTitle("Snake: High Scores");
                    finalScreen.addScore(event.getScore());
                    stage.setScene(finalScreen.getFinalScreen());
                });

        return levelOne;
    }

    public Level getLevelTwo(int score, Snake snake) {
        Level levelTwo = new Level(2, score, snake);

        levelTwo.getScene().addEventHandler(Level.GoLevel1.GO_LEVEL_1,
                event -> {
                    stage.setTitle("Snake: Level One");
                    Level levelOne = getLevelOne(event.getScore(), null);
                    stage.setScene(levelOne.getScene());
                    levelOne.play();
                });
        levelTwo.getScene().addEventHandler(Level.GoLevel3.GO_LEVEL_3,
                event -> {
                    stage.setTitle("Snake: Level Three");
                    Level levelThree = getLevelThree(event.getScore(), event.getSnake());
                    stage.setScene(levelThree.getScene());
                    levelThree.play();
                });
        levelTwo.getScene().addEventHandler(Level.HighScore.HIGH_SCORE,
                event -> {
                    stage.setTitle("Snake: High Scores");
                    finalScreen.addScore(event.getScore());
                    stage.setScene(finalScreen.getFinalScreen());
                });

        return levelTwo;
    }

    public Level getLevelThree(int score, Snake snake) {
        Level levelThree = new Level(3, score, snake);

        levelThree.getScene().addEventHandler(Level.GoLevel1.GO_LEVEL_1,
                event -> {
                    stage.setTitle("Snake: Level One");
                    Level levelOne = getLevelOne(event.getScore(), null);
                    stage.setScene(levelOne.getScene());
                    levelOne.play();
                });
        levelThree.getScene().addEventHandler(Level.GoLevel2.GO_LEVEL_2,
                event -> {
                    stage.setTitle("Snake: Level Two");
                    Level levelTwo = getLevelTwo(event.getScore(), event.getSnake());
                    stage.setScene(levelTwo.getScene());
                    levelTwo.play();
                });
        levelThree.getScene().addEventHandler(Level.HighScore.HIGH_SCORE,
                event -> {
                    stage.setTitle("Snake: High Scores");
                    finalScreen.addScore(event.getScore());
                    stage.setScene(finalScreen.getFinalScreen());
                });
        return levelThree;
    }

    public FinalScreen getFinalScreen() {
        return finalScreen;
    }
}
