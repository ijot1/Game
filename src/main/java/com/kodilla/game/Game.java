package com.kodilla.game;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Game {
    private Board board;
    private GridPane gridPane;
    private Image marker = new Image("file:src/main/resources/board_pawns/marker.png");

    public Game(Board board, GridPane gridPane) {
        this.board = board;
        this.gridPane = gridPane;
    }

    public void showBoard() {
        gridPane.getChildren().clear();
        gridPane.setPrefSize(600, 600);
        gridPane.setPadding(new Insets(-3, 0, 0, 300.0));
        gridPane.getColumnConstraints().add(0, new ColumnConstraints(75));
        gridPane.getColumnConstraints().add(1, new ColumnConstraints(75));
        gridPane.getColumnConstraints().add(2, new ColumnConstraints(75));
        gridPane.getColumnConstraints().add(3, new ColumnConstraints(75));
        gridPane.getColumnConstraints().add(4, new ColumnConstraints(75));
        gridPane.getColumnConstraints().add(5, new ColumnConstraints(75));
        gridPane.getColumnConstraints().add(6, new ColumnConstraints(75));
        gridPane.getColumnConstraints().add(7, new ColumnConstraints(75));
        gridPane.getRowConstraints().add(0, new RowConstraints(75));
        gridPane.getRowConstraints().add(1, new RowConstraints(75));
        gridPane.getRowConstraints().add(2, new RowConstraints(75));
        gridPane.getRowConstraints().add(3, new RowConstraints(75));
        gridPane.getRowConstraints().add(4, new RowConstraints(75));
        gridPane.getRowConstraints().add(5, new RowConstraints(75));
        gridPane.getRowConstraints().add(6, new RowConstraints(75));
        gridPane.getRowConstraints().add(7, new RowConstraints(75));
        gridPane.gridLinesVisibleProperty().set(false);

        //
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Figure figure = board.getFigure(col, row);
                //W gridPane na poz. (col, row) wyświetlić obrazek zależny od figure.getColor()
            }
        }
        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
            gridPane.add(new ImageView(marker), i, i);
//        playingBoard.add(new ImageView(marker), 5, 5);
//                playingBoard.add(new ImageView(), i, j);
        }
//        }
        gridPane.add(new ImageView(marker), 2, 0);
    }
}
