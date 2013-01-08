package solution;

import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Abacus_2_Add_all_balls extends Application {

    private static final int ROW_COUNT = 10;
    private static final int COL_COUNT = 10;
    private static final int RADIUS    = 20;
    private static final int DIAMETER  = 2 * RADIUS;
    private static final int WIDTH     = COL_COUNT * DIAMETER;
    private static final int HEIGHT    = ROW_COUNT * DIAMETER;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Abacus");
        Pane root = new Pane();

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COL_COUNT; column++) {
                Circle circle = makeCircle(row, column);
                root.getChildren().add(circle);
            }
        }
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    private Circle makeCircle(int row, int column) {
        return CircleBuilder.create()
           .radius (RADIUS - 1)
           .centerX(RADIUS + (column * DIAMETER))
           .centerY(RADIUS + (row    * DIAMETER))
           .build();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
