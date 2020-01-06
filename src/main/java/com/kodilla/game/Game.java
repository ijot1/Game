package com.kodilla.game;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Collection;

import static com.kodilla.othello.OthelloApp.*;

public class Game {
    public static final int BOARD_SIZE = 600;
    public static final int TILE_SIZE = 75;
    public static final int PADDING_TOP = 37;

    private Board board;
    private GridPane gridPane;
    private Label blackPlayerName;
    private Label whitePlayerNme;
    private Label pointsBlack;
    private Label pointsWhite;
    private TextField playerName;
    private Label colorDrawInfo;

    private Image marker = new Image("file:src/main/resources/board_pawns/marker.png");
    private Image blackPawn = new Image("file:src/main/resources/board_pawns/blackPawn.png");
    private Image whitePawn = new Image("file:src/main/resources/board_pawns/whitePawn.png");

    public Game(Board board, GridPane gridPane) {
        this.board = board;
        this.gridPane = gridPane;
        blackPlayerName = new Label();
        whitePlayerNme = new Label();
        pointsBlack = new Label();
        pointsWhite = new Label();
        playerName = new TextField();
        colorDrawInfo = new Label("Entering your name you will draw the color");
    }

    public void showBoard() {
        gridPane.getChildren().clear();
        gridPane.setPrefSize(BOARD_SIZE, BOARD_SIZE);
        gridPane.getRowConstraints().add(0, new RowConstraints(37));
        for (int i = 0; i < 16; i++) {
            for (int j = 1; j < 8; j++) {
                gridPane.getColumnConstraints().add(i, new ColumnConstraints(TILE_SIZE));
                gridPane.getRowConstraints().add(j, new RowConstraints(TILE_SIZE));
            }
        }
        gridPane.getRowConstraints().add(9, new RowConstraints(37));

        gridPane.gridLinesVisibleProperty().set(true);

        //add labels etc.
        blackPlayerName.setText(board.getPlayer1());
        blackPlayerName.setMaxWidth(220);
        blackPlayerName.setMaxHeight(35);
        blackPlayerName.setPadding(new Insets(3, 0, 0, 0));
        blackPlayerName.setFont(new Font("Arial", 26));
        blackPlayerName.setTextFill(Color.web("#FFF"));
        blackPlayerName.setText(board.getPlayer1());
        blackPlayerName.setVisible(true);
        gridPane.add(blackPlayerName, 0, 0, 4, 1);
        gridPane.setHalignment(blackPlayerName, HPos.CENTER);

        whitePlayerNme.setText(board.getPlayer2());
        whitePlayerNme.setMaxWidth(220);
        whitePlayerNme.setMaxHeight(35);
        whitePlayerNme.setPadding(new Insets(3, 5, 0, 5));
        whitePlayerNme.setFont(new Font("Arial", 26));
        whitePlayerNme.setTextFill(Color.web("#FFF"));
        whitePlayerNme.setText(board.getPlayer2());
        whitePlayerNme.setVisible(true);
        gridPane.setHalignment(whitePlayerNme, HPos.CENTER);
        gridPane.add(whitePlayerNme, 12, 0,4,1);

        points(pointsBlack);
        pointsBlack.setText(String.valueOf(board.getPointsPlayer1()));
        gridPane.add(pointsBlack, 5, 0, 1, 1);
        gridPane.setHalignment(pointsBlack, HPos.CENTER);

        points(pointsWhite);
        pointsWhite.setText(String.valueOf(board.getPointsPlayer2()));
        gridPane.add(pointsWhite, 10, 0, 1, 1);
        GridPane.setHalignment(pointsWhite, HPos.CENTER);

//        colorDrawInfo = new Label("Entering your name you will draw the color");
        colorDrawInfo.setWrapText(true);
        colorDrawInfo.setTextAlignment(TextAlignment.CENTER);
        colorDrawInfo.setFont(new Font("Arial", 26));
        colorDrawInfo.setStyle("-fx-text-fill: red");
        colorDrawInfo.setVisible(true);
        gridPane.add(colorDrawInfo, 0, 3, 4,1);

//        playerName = new TextField();
        playerName.setPromptText("Enter your name");
        playerName.setFont(new Font("Arial", 26));
        playerName.setStyle("-fx-text-fill: grey");
        playerName.setPadding(new Insets(0, 5, 0,5));
        gridPane.add(playerName, 0,4, 4, 1 );
        playerName.setVisible(true);

        playerName.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String name = playerName.getText();
                int selectBlack = (int)(Math.random() * 2) + 1;
                if (selectBlack == 1) {
                    board.setPlayer1(name);
                    blackPlayerName.setText(name);
                    board.setPlayer2("Computer");
                    whitePlayerNme.setText(board.getPlayer2());
                } else {
                    board.setPlayer1("Computer");
                    blackPlayerName.setText("Computer");
                    board.setPlayer2(name);
                    whitePlayerNme.setText(name);
                }
                colorDrawInfo.setVisible(false);
                playerName.setVisible(false);
                System.out.println(selectBlack);
            }
        });

//        colorDrawInfo.setVisible(false);
//        playerName.setVisible(false);
        for (int col = 4; col < 12; col++) {
            for (int row = 1; row < ROWS_NUMBER; row++) {
                Figure figure = board.getFigure(col, row);
                //pos(col, row) -> picture(figure.getColor())
                if (figure.getColor() == FigureColor.BLACK)
                    gridPane.add(new ImageView(blackPawn), col, row);
                else if (figure.getColor() == FigureColor.WHITE)
                    gridPane.add(new ImageView(whitePawn), col, row);
                else if (figure.getColor() == FigureColor.BLUE)
                    gridPane.add(new ImageView(marker), col, row);
            }
        }

        gridPane.setOnMouseClicked(e -> {
            int x = (int) e.getX()/ TILE_SIZE;
            int y = (int) (e.getY() + PADDING_TOP) / TILE_SIZE;
            System.out.println(x + ", " + y);
            if (x > 3 && x < 12 && y < 9 && y > 0) {
                board.setFigure(x, y, new Figure(colorChoice()));
            }
            showBoard();
        });
            }

    private void points(Label pointsBlack) {
        pointsBlack.setMaxWidth(75);
        pointsBlack.setMaxHeight(35);
        pointsBlack.setPadding(new Insets(3, 0, 0, 0));
        pointsBlack.setFont(new Font("Arial", 26));
        pointsBlack.setTextFill(Color.web("#FFF"));
        pointsBlack.setAlignment(Pos.CENTER);
    }

    public FigureColor colorChoice() {
        return FigureColor.BLUE;
    }
}
