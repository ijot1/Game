package com.kodilla.othello;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OthelloApp extends Application {

    private Image imageback = new Image("file:src/main/resources/background.png");
    private Image blackPawn = new Image("file:src/main/resources/board_pawns/_blackPawn.png");
    private Image whitePawn = new Image("file:src/main/resources/board_pawns/_whitePawn.png");
    private Image marker = new Image("file:src/main/resources/board_pawns/marker.png");

    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage  = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        BorderPane border = new BorderPane();
        border.setPrefSize(1200, 675);
        border.setBackground(background);
        HBox topLabels = addHBox();
        //VBox vBoxLeft = addVBoxLeft();
        GridPane playingBoard = addPlayingBoard();
        //VBox vBoxRight = addVBoxRight();

        //TOP
        border.setTop(topLabels);

        //LEFT
//        border.setLeft(vBoxLeft);

        //CENTER
        border.setCenter(playingBoard);

        //RIGHT
//        border.setRight(vBoxRight);


        Scene scene = new Scene(border, 1200, 675, Color.GRAY);
        primaryStage.setTitle("Othello_Reversi");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public HBox addHBox() {

        String strBlack = "20";
        String strWhite = "46";
        Label pointsBlack = new Label(strBlack);
        Label pointsWhite = new Label(strWhite);
        pointsBlack.setFont(Font.font("Arial", 26));
        pointsBlack.setTextFill(Color.web("#000"));
        pointsWhite.setFont(Font.font("Arial", 26));
        pointsWhite.setTextFill(Color.web("#000"));

        HBox topLabels = new HBox(460);
        topLabels.setPrefHeight(37.5);
        topLabels.setPrefWidth(1200);
        topLabels.setPadding(new Insets(3.5, 0, 5, 340));
        topLabels.getChildren().addAll(pointsBlack, pointsWhite);

        return topLabels;
    }

    public VBox addVBoxLeft() {
        VBox vBoxLeft = new VBox();
        //
        return vBoxLeft;
    }

    public GridPane addPlayingBoard() {
        GridPane playingBoard = new GridPane();
        playingBoard.setPrefSize(600, 600);
        playingBoard.setPadding(new Insets(-3, 0, 0, 300.0));
        playingBoard.getColumnConstraints().add(0, new ColumnConstraints(75));
        playingBoard.getColumnConstraints().add(1, new ColumnConstraints(75));
        playingBoard.getColumnConstraints().add(2, new ColumnConstraints(75));
        playingBoard.getColumnConstraints().add(3, new ColumnConstraints(75));
        playingBoard.getColumnConstraints().add(4, new ColumnConstraints(75));
        playingBoard.getColumnConstraints().add(5, new ColumnConstraints(75));
        playingBoard.getColumnConstraints().add(6, new ColumnConstraints(75));
        playingBoard.getColumnConstraints().add(7, new ColumnConstraints(75));
        playingBoard.getRowConstraints().add(0, new RowConstraints(75));
        playingBoard.getRowConstraints().add(1, new RowConstraints(75));
        playingBoard.getRowConstraints().add(2, new RowConstraints(75));
        playingBoard.getRowConstraints().add(3, new RowConstraints(75));
        playingBoard.getRowConstraints().add(4, new RowConstraints(75));
        playingBoard.getRowConstraints().add(5, new RowConstraints(75));
        playingBoard.getRowConstraints().add(6, new RowConstraints(75));
        playingBoard.getRowConstraints().add(7, new RowConstraints(75));
        playingBoard.gridLinesVisibleProperty().set(false);

//        ImageView bl = new ImageView(blackPawn);
//        ImageView wh = new ImageView(whitePawn);
        //start position
        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
                playingBoard.add(new ImageView(marker), i, i);
//        playingBoard.add(new ImageView(marker), 5, 5);
//                playingBoard.add(new ImageView(), i, j);
            }
//        }
        playingBoard.add(new ImageView(marker), 2, 0);
        //        playingBoard.add(new ImageView(), 4, 4);
        return playingBoard;
    }

    public VBox addVBoxRight() {
        VBox vBoxRight = new VBox();
        //
        return vBoxRight;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
