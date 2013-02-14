package solution;

import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.util.Duration.millis;

public class Abacus_6_Styled extends Application {

    private static final int    ROW_COUNT    = 10;
    private static final int    COL_COUNT    = 10;
    private static final int    RADIUS       = 20;
    private static final int    DIAMETER     = 2 * RADIUS;
    private static final int    MOVE_WAY     = 8 * DIAMETER;
    private static final int    WIDTH        = COL_COUNT * DIAMETER + MOVE_WAY;
    private static final int    HEIGHT       = ROW_COUNT * DIAMETER;
    private static final int    PADDING      = 20;
    private static final int    OFFSET       = PADDING + RADIUS;
    private static final int    RAIL_HEIGHT  = 10;
    private static final String CSS_FILENAME = "/casino.css";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Abacus - styled");
        Pane root = new Pane();

        for (int row = 0; row < ROW_COUNT; row++) {
            Rectangle rail = RectangleBuilder.create()
                .width(WIDTH)
                .height(RAIL_HEIGHT)
                .x(PADDING)
                .y(OFFSET - (RAIL_HEIGHT / 2) + (row * DIAMETER))
                .styleClass("rail").build();
            root.getChildren().add(rail);
            Circle last = null;
            for (int column = 0; column < COL_COUNT; column++) {
                final Circle circle = makeCircle(OFFSET + (row * DIAMETER), OFFSET + (column * DIAMETER));
                circle.getStyleClass().add(column < COL_COUNT / 2 ? "left" : "right");
                root.getChildren().add(circle);

                Text text = new Text(circle.getCenterX()-3, circle.getCenterY()+4, ""+ ((COL_COUNT - column) % 10));
                text.translateXProperty().bind(circle.translateXProperty());
                text.setOnMouseClicked(circle.getOnMouseClicked());
                text.getStyleClass().add("text");
                root.getChildren().add(text);

                if (last != null) {
                    last.translateXProperty().addListener(new ChangeListener<Number>() {
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldX, Number newX) {
                            if ((Double) newX > circle.getTranslateX()) circle.setTranslateX((Double) newX);
                        }
                    });
                    final Circle finalLast = last;
                    circle.translateXProperty().addListener(new ChangeListener<Number>() {
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldX, Number newX) {
                            if ((Double) newX < finalLast.getTranslateX()) finalLast.setTranslateX((Double) newX);
                        }
                    });
                }
                last = circle;
            }
        }
        primaryStage.setScene(new Scene(root, WIDTH + 2 * PADDING, HEIGHT + 2 * PADDING));
        primaryStage.getScene().getStylesheets().add(CSS_FILENAME);
        primaryStage.show();
    }

    private Circle makeCircle(int y, int x) {
        final Circle ball = CircleBuilder.create().radius(RADIUS-1).centerX(x).centerY(y).build();
        ball.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                double newX = MOVE_WAY;
                if (ball.getTranslateX() > 1) {
                    newX = 0;
                }
                TranslateTransition move = TranslateTransitionBuilder.create().node(ball).toX(newX).duration(millis(200)).build();
                move.playFromStart();
            }
        });
        return ball;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
