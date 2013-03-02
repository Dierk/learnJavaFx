package abacus_7;

import groovy.lang.Closure;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
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
import org.opendolphin.binding.Binder;
import org.opendolphin.core.PresentationModel;
import org.opendolphin.core.client.ClientAttribute;
import org.opendolphin.core.client.ClientDolphin;
import org.opendolphin.core.client.ClientPresentationModel;
import org.opendolphin.core.client.comm.OnFinishedHandlerAdapter;

import java.util.List;

import static abacus_7.AbacusConstants.*;
import static javafx.util.Duration.millis;

public class AbacusApplication extends Application {

    static  ClientDolphin           dolphin;
    private ClientPresentationModel touchedBall;

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
    public void start(final Stage primaryStage) {

        touchedBall = dolphin.presentationModel(AbacusConstants.PM_TOUCHED,
            new ClientAttribute(ATT_SCALE),
            new ClientAttribute(ATT_DIGIT),
            new ClientAttribute(ATT_ON));

        dolphin.send(CMD_CREATE_BALLS, new OnFinishedHandlerAdapter() {
            @Override
            public void onFinished(List<ClientPresentationModel> models) {
                primaryStage.setTitle("JavaFX Abacus with Dolphin");
                Pane root = new Pane();

                paintTheRails(root);
                paintEachBallAndBindToModel(root, models);

                primaryStage.setScene(new Scene(root, WIDTH + 2 * PADDING, HEIGHT + 2 * PADDING));
                primaryStage.getScene().getStylesheets().add(CSS_FILENAME);
                primaryStage.show();
            }
        });
    }

    private void paintEachBallAndBindToModel(Pane root, List<ClientPresentationModel> models) {
        for (final ClientPresentationModel ballPm : models) {
            Integer row    = (Integer) ballPm.getAt(ATT_SCALE).getValue();
            Integer digit  = (Integer) ballPm.getAt(ATT_DIGIT).getValue();

            Circle ball = paintBall(root, digit, row);
            Text text   = paintDigit(root, digit, ball);

            onClickToggleOnState(ballPm, ball, text);
            visualizeOnState(ballPm, ball);
        }
    }

    private void paintTheRails(Pane root) {
        for (int scale = 0; scale < ROW_COUNT; scale ++) {
            final Rectangle rail = RectangleBuilder.create()
                .width(WIDTH)
                .height(RAIL_HEIGHT)
                .x(PADDING)
                .y(OFFSET - (RAIL_HEIGHT / 2) + (scale * DIAMETER))
                .styleClass("rail").build();
            root.getChildren().add(rail);
            rail.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    if (rail.getStyleClass().contains("selected"))
                        rail.getStyleClass().remove("selected");
                    else
                        rail.getStyleClass().add("selected");
                }
            });
        }
    }

    private Circle paintBall(Pane root, Integer digit, Integer row) {
        final Circle ball = CircleBuilder.create()
            .radius(RADIUS - 1)
            .centerX(OFFSET + (COL_COUNT - digit) * DIAMETER)
            .centerY(OFFSET + (ROW_COUNT - row - 1) * DIAMETER)
            .build();
        ball.getStyleClass().add(digit > COL_COUNT / 2 ? "left" : "right");
        root.getChildren().add(ball);
        return ball;
    }

    private Text paintDigit(Pane root, Integer digit, Circle ball) {
        Text text = new Text(ball.getCenterX() - 3, ball.getCenterY() + 4, "" + (digit % 10));
        text.translateXProperty().bind(ball.translateXProperty());
        text.getStyleClass().add("text");
        root.getChildren().add(text);
        return text;
    }

    private void onClickToggleOnState(final ClientPresentationModel ballPm, Circle ball, Text text) {
        ball.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                dolphin.apply(ballPm).to(touchedBall);
                dolphin.send(CMD_TOGGLE);
            }
        });
        text.setOnMouseClicked(ball.getOnMouseClicked());
    }

    private void visualizeOnState(PresentationModel ballPm, final Circle ball) {
        Binder.bind(ATT_ON).of(ballPm).to("translateX").of(ball, new Closure(this) {
            public Object call(Boolean on) {
                Double result = on ? MOVE_WAY : 0d;
                TranslateTransitionBuilder.create().node(ball).toX(result).duration(millis(200)).build().playFromStart();
                return result;
            }
        });
    }

}