import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {
    private final Pane paneBoard;

    Board(int cellsX, int cellsY, int cellSize) {
        paneBoard = new Pane();
        paneBoard.setPrefWidth(cellsX * cellSize);
        paneBoard.setPrefHeight(cellsY * cellSize);
        paneBoard.setMaxWidth(Region.USE_PREF_SIZE);
        paneBoard.setMaxHeight(Region.USE_PREF_SIZE);

        Rectangle clip = new Rectangle(cellsX * cellSize, cellsY * cellSize);
        paneBoard.setClip(clip);

        Group board = new Group();
        board.setAutoSizeChildren(false);
        for (int i = 0; i < cellsX; ++i) {
            for (int j = 0; j < cellsY; ++j) {
                Rectangle rect = new Rectangle(
                        i * cellSize, j * cellSize, cellSize, cellSize);
                if ((i + j) % 2 == 0) {
                    rect.setFill(Color.color(0.2, 0.8, 0));
                } else {
                    rect.setFill(Color.LAWNGREEN);
                }
                board.getChildren().add(rect);
            }
        }
        paneBoard.getChildren().add(board);
    }

    public Pane getBoard () {
        return paneBoard;
    }

}
