package com.kodilla.chessboard;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Szachownica extends Application {

    public Parent createContent() {

        BorderPane layout = new BorderPane();

        VBox vBox = new VBox();
        for (int i = 0; i < 8; ++i) {
            String blackOrWhite;
            if (i % 2 == 0) {
                blackOrWhite = "Black";
            } else {
                blackOrWhite = "White";
            }
            HBox hBox = new HBox();
            for (int j = 0; j < 8; j++) {
                Label label = new Label();
                label.setMinSize(40, 40);
                if (blackOrWhite.equals("Black")) {
                    label.setStyle("-fx-background-color: #000000");
                    blackOrWhite = "White";
                } else {
                    label.setStyle("-fx-background-color: #ffffff");
                    blackOrWhite = "Black";
                }
                hBox.getChildren().add(label);
            }
            vBox.getChildren().add(hBox);
        }

        vBox.setAlignment(Pos.CENTER);
        layout.setCenter(vBox);

        return layout;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.setWidth(340);
        stage.setHeight(360);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
