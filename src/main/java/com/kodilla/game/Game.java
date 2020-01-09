package com.kodilla.game;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.game.FigureColor.*;
import static com.kodilla.othello.OthelloApp.ROWS_NUMBER;

public class Game {
    public static final int BOARD_SIZE = 600;
    public static final int TILE_SIZE = 75;
    public static final int PADDING_TOP = 37;

    private Board board;
    private GridPane gridPane;
    private Participants participants;
    private Label blackPlayerName;
    private Label whitePlayerNme;
    private Label pointsBlack;
    private Label pointsWhite;
    private TextField playerName;
    private Label colorDrawInfo;
    private FigureColor whoseMove = BLACK;
    private FigureColor humanColor;
    private List<Move> acceptableMoves = new ArrayList<>();


    private Image marker = new Image("file:src/main/resources/board_pawns/marker.png");
    private Image blackPawn = new Image("file:src/main/resources/board_pawns/blackPawn.png");
    private Image whitePawn = new Image("file:src/main/resources/board_pawns/whitePawn.png");
    private Object field;

    public Game(Board board, GridPane gridPane) {
        this.board = board;
        this.gridPane = gridPane;
        this.participants = new Participants();
        blackPlayerName = new Label();
        whitePlayerNme = new Label();
        pointsBlack = addPointsLabel(0);
        pointsWhite = addPointsLabel(0);
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
        blackPlayerName.setText(participants.getPlayer1());
        blackPlayerName.setMaxWidth(220);
        blackPlayerName.setMaxHeight(35);
        blackPlayerName.setPadding(new Insets(3, 0, 0, 0));
        blackPlayerName.setFont(new Font("Arial", 26));
        blackPlayerName.setTextFill(Color.web("#FFF"));
        blackPlayerName.setText(participants.getPlayer1());
        gridPane.add(blackPlayerName, 0, 0, 4, 1);
        gridPane.setHalignment(blackPlayerName, HPos.CENTER);

        whitePlayerNme.setText(participants.getPlayer2());
        whitePlayerNme.setMaxWidth(220);
        whitePlayerNme.setMaxHeight(35);
        whitePlayerNme.setPadding(new Insets(3, 5, 0, 5));
        whitePlayerNme.setFont(new Font("Arial", 26));
        whitePlayerNme.setTextFill(Color.web("#FFF"));
        whitePlayerNme.setText(participants.getPlayer2());
        gridPane.setHalignment(whitePlayerNme, HPos.CENTER);
        gridPane.add(whitePlayerNme, 12, 0, 4, 1);

        pointsBlack = addPointsLabel(participants.getPointsPlayer1());
        gridPane.add(pointsBlack, 5, 0, 1, 1);
        gridPane.setHalignment(pointsBlack, HPos.LEFT);

        pointsWhite = addPointsLabel(participants.getPointsPlayer2());
        gridPane.add(pointsWhite, 10, 0, 1, 1);
        gridPane.setHalignment(pointsWhite, HPos.RIGHT);

        colorDrawInfo.setWrapText(true);
        colorDrawInfo.setTextAlignment(TextAlignment.CENTER);
        colorDrawInfo.setFont(new Font("Arial", 26));
        colorDrawInfo.setStyle("-fx-text-fill: red");
        gridPane.add(colorDrawInfo, 0, 3, 4, 1);

        playerName.setPromptText("Enter your name");
        playerName.setFont(new Font("Arial", 26));
        playerName.setStyle("-fx-text-fill: grey");
        playerName.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(playerName, 0, 4, 4, 1);

        setInitialView();

        playerName.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String name = playerName.getText();
                int selectBlack = (int) (Math.random() * 2) + 1;
                if (selectBlack == 1) {
                    participants.setPlayer1(name);
                    blackPlayerName.setText(name);
                    participants.setPlayer2("Computer");
                    whitePlayerNme.setText(participants.getPlayer2());
                    humanColor = BLACK;
                } else {
                    participants.setPlayer1("Computer");
                    participants.setPlayer2(name);
                    blackPlayerName.setText("Computer");
                    whitePlayerNme.setText(name);
                    humanColor = WHITE;
                    calculateMoves();
                    setAcceptableMoves();
                    doComputerMove();
                    calculatePoints();
                }
                colorDrawInfo.setVisible(false);
                playerName.setVisible(false);
                showFigures();
            }
        });

        gridPane.setOnMouseClicked(e -> {
            if (humanColor == whoseMove) {
                int x = (int) e.getX() / TILE_SIZE;
                int y = (int) (e.getY() + PADDING_TOP) / TILE_SIZE;
                System.out.println(x + ", " + y + "; " + acceptableMoves.size());
                if (x > 3 && x < 12 && y < 9 && y > 0 && board.getFigure(x, y).getColor() == BLUE) {
                    board.setFigure(x, y, new Figure(humanColor));
                    calculateMoves();
                    setAcceptableMoves();
                    calculatePoints();
                    switchPlayer();
                    doComputerMove();
                    calculatePoints();
                }
            } else {
                calculateMoves();
                setAcceptableMoves();
                doComputerMove();
                calculatePoints();
            }
            showFigures();

        });
    }

    private void setAcceptableMoves() {
        for (int i = 0; i < acceptableMoves.size(); i++) {
            board.setFigure(acceptableMoves.get(i).getField().getX(), acceptableMoves.get(i).getField().getY(), new Figure(BLUE));
        }
    }

    private void calculateMoves() {
        List<Field> delta = new ArrayList<>();
        //NE clockwise
        delta.add(new Field(1, -1));
        delta.add(new Field(1, 0));
        delta.add(new Field(1, 1));
        delta.add(new Field(0, 1));
        delta.add(new Field(-1, 1));
        delta.add(new Field(-1, 0));
        delta.add(new Field(-1, -1));
        delta.add(new Field(0, -1));

        FigureColor tempColor = whoseMove;
        tempColor = (tempColor == WHITE) ? BLACK : WHITE;
        Field tempField;

        for (int col = 4; col < 12; col++) {
            for (int row = 1; row < ROWS_NUMBER; row++) {
                if (board.getFigure(col, row).getColor() == NONE) {
                    int direction = 0;
                    tempField = new Field(col, row);
                    do {
                        Field fAdd = tempField.addField(delta.get(direction));
                        Move m = null;
                        if (board.getFigure(fAdd.getX(), fAdd.getY()).getColor() == tempColor) {
                            m = new Move();
                            m.setField(tempField);
                            m.getToScore().add(fAdd);
                        } else if (board.getFigure(fAdd.getX(), fAdd.getY()).getColor() == whoseMove) {
                            acceptableMoves.add(m);
                            direction++;
                        } else {
                            direction++;
                        }
                    } while (direction < delta.size());
                }
            }
        }
    }

    private void calculatePoints() {
        int b = 0;
        int w = 0;
        for (int col = 4; col < 12; col++) {
            for (int row = 1; row < ROWS_NUMBER; row++) {
                Figure figure = board.getFigure(col, row);
                if (figure.getColor() == BLACK)
                    b++;
                else if (figure.getColor() == FigureColor.WHITE)
                    w++;
            }
        }
        participants.setPointsPlayer1(b);
        participants.setPointsPlayer2(w);
        pointsBlack.setText(String.valueOf(b));
        pointsWhite.setText(String.valueOf(w));
    }

    private void showFigures() {
        for (int col = 4; col < 12; col++) {
            for (int row = 1; row < ROWS_NUMBER; row++) {
                Figure figure = board.getFigure(col, row);
                if (figure.getColor() == BLACK)
                    gridPane.add(new ImageView(blackPawn), col, row);
                else if (figure.getColor() == FigureColor.WHITE)
                    gridPane.add(new ImageView(whitePawn), col, row);
                else if (figure.getColor() == FigureColor.BLUE)
                    gridPane.add(new ImageView(marker), col, row);
            }
        }
    }

    private void setInitialView() {
        board.setFigure(7, 4, new Figure(WHITE));
        board.setFigure(8, 5, new Figure(WHITE));
        board.setFigure(7, 5, new Figure(BLACK));
        board.setFigure(8, 4, new Figure(BLACK));
    }

    private void switchPlayer() {
        whoseMove = (whoseMove == FigureColor.WHITE) ? BLACK : FigureColor.WHITE;
    }

    private void doComputerMove() {
        List<Field> computerMoves = new ArrayList<>();
        for (int i = 4; i < 12; i++) {
            for (int j = 1; j < 9; j++) {
                if (board.getFigure(i, j).getColor() == BLUE) {
                    Field field = new Field(i, j);
                    computerMoves.add(field);
                }
            }
        }
        int random = (int) (Math.random() * computerMoves.size());
        Field toField = computerMoves.get(random);
        board.setFigure(toField.getX(), toField.getY(), new Figure(whoseMove));
        showFigures();
        switchPlayer();
        System.out.println(computerMoves.size() + "; " + random + "; " + whoseMove);
    }

    private Label addPointsLabel(int p) {
        Label l = new Label(String.valueOf(p));
        l.setMaxWidth(75);
        l.setMaxHeight(35);
        l.setPadding(new Insets(3, 0, 0, 0));
        l.setFont(new Font("Arial", 26));
        l.setTextFill(Color.web("#FFF"));
        l.setAlignment(Pos.CENTER);
        return l;
    }

    public FigureColor colorChoice() {
        return FigureColor.BLUE;
    }
}
