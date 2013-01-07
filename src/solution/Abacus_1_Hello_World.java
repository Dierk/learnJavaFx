package solution;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;

public class Abacus_1_Hello_World extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Abacus");
        Pane root = new Pane();

        Circle circle = CircleBuilder.create()
            .radius(20)
            .centerX(20)
            .centerY(20)
            .build();
        root.getChildren().add(circle);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
