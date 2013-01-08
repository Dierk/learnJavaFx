package solution;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;

public class Abacus_2_Add_all_balls_and_onClick extends Application {

    private static final int ROW_COUNT = 10;
    private static final int COL_COUNT = 10;
    private static final int RADIUS    = 20;
    private static final int DIAMETER  = 2 * RADIUS;
    private static final int WIDTH     = COL_COUNT * DIAMETER;
    private static final int HEIGHT    = ROW_COUNT * DIAMETER;
    private static final int MOVE_WAY  = 100;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Abacus");
        Pane root = new Pane();

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COL_COUNT; column++) {
                final Circle circle = makeCircle(row, column);
                root.getChildren().add(circle);
                onClick(circle);
            }
        }
        primaryStage.setScene(new Scene(root, WIDTH + MOVE_WAY, HEIGHT));
        primaryStage.show();
    }

    private void onClick(final Circle circle) {
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                circle.setTranslateX(MOVE_WAY);
            }
        });
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
