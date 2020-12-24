import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Level {
    private static final int cellsX = 30, cellsY = 20, cellSize = 30;
    private Scene scene;
    private Board board;
    private Snake snake;
    private Fruit fruit;

    private int difficulty;
    private int direction; // 0 straight, 1 left, 2 right
    private boolean paused;

    private int score;
    private int fruitsEaten;
    private double clock;
    private Text scoreText;
    private Text fruitsEatenText;
    private Text clockText;

    private AnimationTimer gameTimer;
    private long prevTime;

    private final int SCREEN_WIDTH = 1280;
    private final int SCREEN_HEIGHT = 800;

    Level(int difficulty, int score, Snake snake) {
        this.difficulty = difficulty;
        direction = 0;
        paused = true;

        this.score = score;
        fruitsEaten = 0;
        clock = 30.9;

        StackPane group = new StackPane();
        group.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, null, null)));

        board = new Board(cellsX, cellsY, cellSize);
        //Group g_board = board.getBoard();
        Pane g_board = board.getBoard();
        StackPane.setAlignment(g_board, Pos.CENTER);
        group.getChildren().add(g_board);

        if (snake == null) {
            this.snake = new Snake(difficulty);
            Group g_snake = this.snake.getSnake();
            g_board.getChildren().add(g_snake);
        } else {
            this.snake = new Snake(difficulty, snake);
            Group g_snake = this.snake.getSnake();
            g_board.getChildren().add(g_snake);
        }

        fruit = new Fruit(difficulty * 5, this.snake);
        Group g_fruits = fruit.getFruits();
        g_board.getChildren().add(g_fruits);

        fruitsEatenText = new Text("Level " + difficulty + "\nFruits Eaten: " + fruitsEaten);
        fruitsEatenText.setFill(Color.WHITE);
        fruitsEatenText.setFont(Font.font("Trebuchet MS", 40));
        StackPane.setAlignment(fruitsEatenText, Pos.TOP_LEFT);
        group.getChildren().add(fruitsEatenText);

        if (difficulty != 3) {
            clockText = new Text(Integer.toString((int)clock));
            clockText.setFill(Color.WHITE);
            clockText.setFont(Font.font("Trebuchet MS", 40));
            StackPane.setAlignment(clockText, Pos.TOP_CENTER);
            group.getChildren().add(clockText);
        }

        scoreText = new Text("Score: " + score);
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Trebuchet MS", 40));
        StackPane.setAlignment(scoreText, Pos.TOP_RIGHT);
        group.getChildren().add(scoreText);

        scene = new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);

        initGameTimer();

        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                if (direction == 0) {
                    direction = 1;
                }
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                if (direction == 0) {
                    direction = 2;
                }
            } else if (keyEvent.getCode() == KeyCode.P) {
                if (paused) {
                    play();
                } else {
                    gameTimer.stop();
                    paused = true;
                }
            } else if (keyEvent.getCode() == KeyCode.DIGIT1) {
                if (difficulty != 1) {
                    gameTimer.stop();
                    ((Node) board.getBoard()).fireEvent(new GoLevel1(0));
                }
            } else if (keyEvent.getCode() == KeyCode.DIGIT2) {
                if (difficulty != 2) {
                    gameTimer.stop();
                    ((Node) board.getBoard()).fireEvent(new GoLevel2(0, null));
                }
            } else if (keyEvent.getCode() == KeyCode.DIGIT3) {
                if (difficulty != 3) {
                    gameTimer.stop();
                    ((Node) board.getBoard()).fireEvent(new GoLevel3(0, null));
                }
            } else if (keyEvent.getCode() == KeyCode.Q) {
                gameTimer.stop();
                ((Node) board.getBoard()).fireEvent(new HighScore(this.score));
            }
        });
    }

    public void initGameTimer() {
        gameTimer = new AnimationTimer() {
            final int threshold = cellSize / snake.getSpeed();
            int timer = 0;

            @Override
            public void handle(long now) {
                double elapsed = (now - prevTime) / 1000000000.0;
                //System.out.println(elapsed);
                clock -= elapsed;

                if (timer == threshold) {
                    if (direction == 1) {
                        snake.turnLeft();
                    } else if (direction == 2) {
                        snake.turnRight();
                    }
                    direction = 0;
                    timer = 0;

                    if (difficulty != 3) {
                        clockText.setText(Integer.toString((int) clock));
                        if (clock <= 0) {
                            if (difficulty == 1) {
                                ((Node) board.getBoard()).fireEvent(new GoLevel2(score, snake));
                            } else {
                                ((Node) board.getBoard()).fireEvent(new GoLevel3(score, snake));
                            }
                            stop();
                            return;
                        }
                    }
                } if (timer == 1) {
                    if (hitWall()) {
                        stop();
                        ((Node) board.getBoard()).fireEvent(new HighScore(score));
                        return;
                    } else if (hitSelf()) {
                        stop();
                        ((Node) board.getBoard()).fireEvent(new HighScore(score));
                        return;
                    }
                    eatFruit();
                    fruitsEatenText.setText("Level " + difficulty + "\nFruits Eaten: " + fruitsEaten);
                    scoreText.setText("Score: " + score);
                }
                snake.move();
                timer++;
                prevTime = now;
            }
        };
    }

    public void play() {
        prevTime = System.nanoTime();
        gameTimer.start();
        paused = false;
    }

    public Scene getScene() {
        return scene;
    }

    public void eatFruit() {
        Shape head = snake.getNodes().get(0);

        double x = ((Polygon) head).getPoints().get(4);
        double y = ((Polygon) head).getPoints().get(5);
        Point2D tip = head.getLocalToParentTransform().transform(x, y);

        Group fruits = fruit.getFruits();
        for (Node curFruit: fruits.getChildren()) {
            Bounds bounds = curFruit.getBoundsInParent();
            if (bounds.contains(tip)) {
                fruits.getChildren().remove(curFruit);
                snake.grow();
                fruit.generateFruit(snake.getNodes());

                fruitsEaten++;
                score += 10;
                return;
            }
        }
    }

    public boolean hitWall () {
        Shape head = snake.getNodes().get(0);

        double x = ((Polygon) head).getPoints().get(4);
        double y = ((Polygon) head).getPoints().get(5);
        Point2D tip = head.getLocalToParentTransform().transform(x, y);

        if (tip.getX() < 0 || tip.getX() >= cellsX * cellSize) {
            return true;
        }
        if (tip.getY() < 0 || tip.getY() >= cellsY * cellSize) {
            return true;
        }
        return false;
    }

    public boolean hitSelf () {
        Shape head = snake.getNodes().get(0);

        double x = ((Polygon) head).getPoints().get(4);
        double y = ((Polygon) head).getPoints().get(5);
        Point2D tip = head.getLocalToParentTransform().transform(x, y);

        for (int i = 1; i < snake.getNodes().size(); ++i) {
            Bounds bounds = snake.getNodes().get(i).getBoundsInParent();
            if (bounds.contains(tip)) {
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    static class GoLevel1 extends Event {
        private int score;

        public static final EventType<GoLevel1> GO_LEVEL_1 =
                new EventType<>(Event.ANY, "GO_LEVEL_1");

        public GoLevel1(int score) {
            super(GO_LEVEL_1);
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        @Override
        public EventType<GoLevel1> getEventType() {
            return (EventType<GoLevel1>) super.getEventType();
        }
    }

    static class GoLevel2 extends Event {
        private int score;
        private Snake snake;

        public static final EventType<GoLevel2> GO_LEVEL_2 =
                new EventType<>(Event.ANY, "GO_LEVEL_2");

        public GoLevel2(int score, Snake snake) {
            super(GO_LEVEL_2);
            this.score = score;
            this.snake = snake;
        }

        public int getScore() {
            return score;
        }

        public Snake getSnake() {
            return snake;
        }

        @Override
        public EventType<GoLevel2> getEventType() {
            return (EventType<GoLevel2>) super.getEventType();
        }
    }

    static class GoLevel3 extends Event {
        private int score;
        private Snake snake;

        public static final EventType<GoLevel3> GO_LEVEL_3 =
                new EventType<>(Event.ANY, "GO_LEVEL_3");

        public GoLevel3(int score, Snake snake) {
            super(GO_LEVEL_3);
            this.score = score;
            this.snake = snake;
        }

        public int getScore() {
            return score;
        }

        public Snake getSnake() {
            return snake;
        }

        @Override
        public EventType<GoLevel3> getEventType() {
            return (EventType<GoLevel3>) super.getEventType();
        }
    }

    static class HighScore extends Event {
        private int score;

        public static final EventType<HighScore> HIGH_SCORE =
                new EventType<>(Event.ANY, "HIGH_SCORE");

        public HighScore(int score) {
            super(HIGH_SCORE);
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        @Override
        public EventType<HighScore> getEventType() {
            return (EventType<HighScore>) super.getEventType();
        }
    }

    static class GoSplashScreen extends Event {
        public static final EventType<GoSplashScreen> GO_SPLASH_SCREEN =
                new EventType<>(Event.ANY, "GO_SPLASH_SCREEN");

        public GoSplashScreen() {
            super(GO_SPLASH_SCREEN);
        }

        @Override
        public EventType<GoSplashScreen> getEventType() {
            return (EventType<GoSplashScreen>) super.getEventType();
        }
    }
}
