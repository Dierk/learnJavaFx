package solution;

import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;

import static javafx.util.Duration.millis;

public class Abacus_3_Ball_transition extends Application {

    private static final int    ROW_COUNT    = 10;
    private static final int    COL_COUNT    = 10;
    private static final int    RADIUS       = 20;
    private static final int    DIAMETER     = 2 * RADIUS;
    private static final int    MOVE_WAY     = 8 * DIAMETER;
    private static final int    WIDTH        = COL_COUNT * DIAMETER + MOVE_WAY;
    private static final int    HEIGHT       = ROW_COUNT * DIAMETER;
    private static final int    PADDING      = 20;
    private static final int    OFFSET       = PADDING + RADIUS;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Abacus");
        Pane root = new Pane();

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COL_COUNT; column++) {
                final Circle circle = makeCircle(OFFSET + (row * DIAMETER), OFFSET + (column * DIAMETER));
                root.getChildren().add(circle);
            }
        }
        primaryStage.setScene(new Scene(root, WIDTH + 2 * PADDING, HEIGHT + 2 * PADDING));
        primaryStage.show();
    }

    private Circle makeCircle(int y, int x) {
        final Circle ball = CircleBuilder.create().radius(RADIUS-1).centerX(x).centerY(y).build();
        ball.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                double newX = MOVE_WAY;
                if (ball.getTranslateX() > 1) newX = 0;
                TranslateTransition move = TranslateTransitionBuilder.create()
                    .node(ball)
                    .toX(newX)
                    .duration(millis(200))
                    .build();
                move.playFromStart();
                }
        });
        return ball;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
