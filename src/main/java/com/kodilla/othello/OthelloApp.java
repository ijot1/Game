package com.kodilla.othello;

import com.kodilla.game.Board;
import com.kodilla.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OthelloApp extends Application {

    public static final int ROWS_NUMBER = 10;
    public static final int COLS_NUMBER = 16;
    public static final int SCENE_WIDTH = 1200;
    public static final int SCENE_HEIGHT = 675;

    private Image imageback = new Image("file:src/main/resources/background.png");

    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane playingBoard = addPlayingBoard();
        playingBoard.setBackground(background);

        Scene scene = new Scene(playingBoard, SCENE_WIDTH, SCENE_HEIGHT, Color.GRAY);
        primaryStage.setTitle("Othello_Reversi");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
}

    public GridPane addPlayingBoard() {
        Board board = new Board();
        GridPane playingBoard = new GridPane();
        Game game = new Game(board, playingBoard);
        game.showBoard();

        return playingBoard;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
