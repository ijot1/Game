package com.kodilla.game;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.*;

import static com.kodilla.game.FigureColor.*;
import static com.kodilla.othello.OthelloApp.ROWS_NUMBER;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.random;

public class Game {
    public static final int BOARD_SIZE = 600;
    public static final int TILE_SIZE = 75;
    public static final int PADDING_TOP = 37;
    public static final int ONE_PLAYER = 1;
    public static final int TWO_PLAYERS = 2;
    public static final int PLUS_INFINITY = 65;
    public static final int MINUS_INFINITY = -65;

    private Board board;
    private GridPane gridPane;
    private Participants participants;
    private Label blackPlayerName;
    private Label whitePlayerNme;
    private Label moveBlack;
    private Label moveWhite;
    private Label pointsBlack;
    private Label pointsWhite;
    private Label infoBasicOrHigh;
    private Label infoLevel;
    private ToggleGroup choiceOneOrTwoPlayers;
    private ToggleGroup choiceLevel;
    private ToggleButton onePlayer;
    private ToggleButton twoPlayers;
    private ToggleButton basicLevel;
    private ToggleButton highLevel;
    private TextField player1rName;
    private TextField player2rName;
    private Button button1;
    private Button button2;
    private Button backToStart;
    private Button playAgain;
    private Label whoPlays;
    private Label colorDrawInfo;
    private FigureColor whoseMove = BLACK;
    private FigureColor player1Color;
    private ArrayList<Move> acceptableMoves;
    private int players;
    private boolean levelChoice = false;

    private Image marker = new Image("file:src/main/resources/board_pawns/marker.png");
    private Image blackPawn = new Image("file:src/main/resources/board_pawns/blackPawn.png");
    private Image whitePawn = new Image("file:src/main/resources/board_pawns/whitePawn.png");
    private Image empty = new Image("file:src/main/resources/board_pawns/empty.png");

    public Game(Board board, GridPane gridPane) {
        this.board = board;
        this.gridPane = gridPane;
        this.participants = new Participants();

        blackPlayerName = new Label();
        whitePlayerNme = new Label();

        moveBlack = addInfo("move");
        moveWhite = addInfo("move");

        infoBasicOrHigh = addInfo("");
        infoLevel = addInfo("LEVEL");

        pointsBlack = addPointsLabel(0);
        pointsWhite = addPointsLabel(0);

        player1rName = new TextField();
        player1rName.setPromptText("Get player 1 name");
        player1rName.setVisible(false);
        player2rName = new TextField();
        player2rName.setPromptText("Get player 2 name");
        player2rName.setVisible(false);
        button1 = new Button("Submit");
        button1.setVisible(false);
        button2 = new Button("Submit");
        button2.setVisible(false);
        backToStart = new Button("Back to\n  start");
        backToStart.setVisible(false);
        playAgain = new Button("Play again");
        playAgain.setVisible(false);

        // Creating Toggle buttons
        onePlayer = new ToggleButton("One player");
        twoPlayers = new ToggleButton("Two players");
        basicLevel = new ToggleButton("Basic level");
        highLevel = new ToggleButton("High level");

        // Creating a ToggleGroup
        choiceOneOrTwoPlayers = new ToggleGroup();
        choiceLevel = new ToggleGroup();

        // Add ToggleButtons to a ToggleGroup
        choiceOneOrTwoPlayers.getToggles().addAll(onePlayer, twoPlayers);
        choiceLevel.getToggles().addAll(basicLevel, highLevel);

        //Info labels
        whoPlays = new Label();
        colorDrawInfo = new Label();
        colorDrawInfo.setVisible(false);
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

        gridPane.gridLinesVisibleProperty().set(false);

        // Add labels etc.
        blackPlayerName.setText(participants.getPlayer1());
        blackPlayerName.setMaxWidth(220);
        blackPlayerName.setMaxHeight(35);
        blackPlayerName.setPadding(new Insets(3, 0, 0, 0));
        blackPlayerName.setFont(new Font("Arial", 26));
        blackPlayerName.setTextFill(Color.web("#FFF"));
        blackPlayerName.setText(participants.getPlayer1());
        gridPane.add(blackPlayerName, 0, 0, 3, 1);
        gridPane.setHalignment(blackPlayerName, HPos.CENTER);

        whitePlayerNme.setText(participants.getPlayer2());
        whitePlayerNme.setMaxWidth(220);
        whitePlayerNme.setMaxHeight(35);
        whitePlayerNme.setPadding(new Insets(3, 5, 0, 5));
        whitePlayerNme.setFont(new Font("Arial", 26));
        whitePlayerNme.setTextFill(Color.web("#FFF"));
        whitePlayerNme.setText(participants.getPlayer2());
        gridPane.setHalignment(whitePlayerNme, HPos.CENTER);
        gridPane.add(whitePlayerNme, 13, 0, 3, 1);


        gridPane.add(moveBlack, 3, 0, 1, 1);
        gridPane.add(moveWhite, 12, 0, 1, 1);
        infoBasicOrHigh.setStyle("-fx-text-fill: blue");
        gridPane.add(infoBasicOrHigh, 7, 0, 1, 1);
        infoLevel.setStyle("-fx-text-fill: blue");
        gridPane.add(infoLevel, 8, 0, 1, 1);


        pointsBlack = addPointsLabel(participants.getPointsPlayer1());
        gridPane.add(pointsBlack, 5, 0, 1, 1);
        gridPane.setHalignment(pointsBlack, HPos.LEFT);

        pointsWhite = addPointsLabel(participants.getPointsPlayer2());
        gridPane.add(pointsWhite, 10, 0, 1, 1);
        gridPane.setHalignment(pointsWhite, HPos.RIGHT);

        whoPlays.setWrapText(true);
        whoPlays.setTextAlignment(TextAlignment.CENTER);
        whoPlays.setFont(new Font("Arial", 20));
        whoPlays.setStyle("-fx-text-fill: blue");
        whoPlays.setText("One player plays vs. computer\nor\nTwo players play vs. each other");
        gridPane.add(whoPlays, 0, 2, 4, 1);

        colorDrawInfo.setWrapText(true);
        colorDrawInfo.setTextAlignment(TextAlignment.CENTER);
        colorDrawInfo.setFont(new Font("Arial", 26));
        colorDrawInfo.setStyle("-fx-text-fill: red");
        gridPane.add(colorDrawInfo, 0, 5, 4, 1);

        player1rName.setFont(new Font("Arial", 26));
        player1rName.setStyle("-fx-text-fill: grey");
        player1rName.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(player1rName, 0, 3, 3, 1);

        player2rName.setFont(new Font("Arial", 26));
        player2rName.setStyle("-fx-text-fill: grey");
        player2rName.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(player2rName, 0, 4, 3, 1);

        button1.setFont(new Font("Arial", 16));
        button1.setStyle("-fx-text-fill: grey");
        button1.setMaxSize(75, 75);
        button1.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(button1, 3, 3, 1, 1);

        button2.setFont(new Font("Arial", 16));
        button2.setStyle("-fx-text-fill: grey");
        button2.setMaxSize(75, 75);
        button2.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(button2, 3, 4, 1, 1);

        backToStart.setFont(new Font("Arial", 26));
        backToStart.setStyle("-fx-text-fill: grey");
        backToStart.setMaxSize(150, 75);
        backToStart.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(backToStart, 1, 6, 2, 1);

        playAgain.setFont(new Font("Arial", 26));
        playAgain.setStyle("-fx-text-fill: grey");
        playAgain.setMaxSize(150, 75);
        playAgain.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(playAgain, 13, 1, 2, 1);

        onePlayer.setWrapText(true);
        onePlayer.setMaxSize(74, 74);
        onePlayer.setTextAlignment(TextAlignment.CENTER);
        onePlayer.setFont(new Font("Arial", 16));
        onePlayer.setStyle("-fx-text-fill: grey");
        onePlayer.setVisible(true);
        gridPane.add(onePlayer, 0, 1, 1, 1);

        twoPlayers.setWrapText(true);
        twoPlayers.setMaxSize(74, 74);
        twoPlayers.setTextAlignment(TextAlignment.CENTER);
        twoPlayers.setFont(new Font("Arial", 16));
        twoPlayers.setStyle("-fx-text-fill: grey");
        twoPlayers.setVisible(true);
        gridPane.add(twoPlayers, 1, 1, 1, 1);

        basicLevel.setWrapText(true);
        basicLevel.setMaxSize(74, 74);
        basicLevel.setTextAlignment(TextAlignment.CENTER);
        basicLevel.setFont(new Font("Arial", 16));
        basicLevel.setStyle("-fx-text-fill: grey");
        basicLevel.setVisible(false);
        gridPane.add(basicLevel, 2, 1, 1, 1);

        highLevel.setWrapText(true);
        highLevel.setMaxSize(74, 74);
        highLevel.setTextAlignment(TextAlignment.CENTER);
        highLevel.setFont(new Font("Arial", 16));
        highLevel.setStyle("-fx-text-fill: grey");
        highLevel.setVisible(false);
        gridPane.add(highLevel, 3, 1, 1, 1);

        setInitialView();


    }

    public void prepareButtons() {
        choiceOneOrTwoPlayers.selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
            ToggleButton button = (newToggle != null) ? (ToggleButton)newToggle : (ToggleButton)oldToggle;
            String toggleMsg = button.getText();
            if (Objects.equals(toggleMsg, "One player")) {
                players = ONE_PLAYER;
                whoPlays.setText("Your selection:\n" + toggleMsg + " vs. computer\n SELECT LEVEL");
                basicLevel.setVisible(true);
                highLevel.setVisible(true);
                backToStart.setVisible(true);
            } else {
                players = TWO_PLAYERS;
                whoPlays.setText("Your selection:\n" + toggleMsg + " play vs. each other");
                colorDrawInfo.setText("Entering the names starts color drawing");
                colorDrawInfo.setVisible(true);
                player1rName.requestFocus();
                player1rName.setVisible(true);
                button1.setVisible(true);
                backToStart.setVisible(true);
                startPlay();
            }
            onePlayer.setVisible(false);    //ToggleButton
            twoPlayers.setVisible(false);   //ToggleButton
        });

        choiceLevel.selectedToggleProperty().addListener((ov, oldToggle, newToggle) -> {
            ToggleButton button = (newToggle != null) ? (ToggleButton)newToggle : (ToggleButton)oldToggle;
            String toggleMsg = button.getText();
            if (Objects.equals(toggleMsg, "Basic level")) {
                levelChoice = false;
                infoBasicOrHigh.setText("BASIC");
                infoBasicOrHigh.setVisible(true);
                infoLevel.setVisible(true);
            } else {
                levelChoice = true;
                infoBasicOrHigh.setText("HIGH");
                infoBasicOrHigh.setVisible(true);
                infoLevel.setVisible(true);
            }
            whoPlays.setVisible(false);
            colorDrawInfo.setText("Entering the name starts color drawing");
            colorDrawInfo.setVisible(true);
            basicLevel.setVisible(false);
            highLevel.setVisible(false);
            player1rName.requestFocus();
            player1rName.setVisible(true);
            button1.setVisible(true);
            startPlay();
        });

        gridPane.setOnMouseClicked(e -> {
            int x = (int) e.getX() / TILE_SIZE;
            int y = (int) (e.getY() + PADDING_TOP) / TILE_SIZE;

            if (x > 3 && x < 12 && y < 9 && y > 0 && board.getFigure(x, y).getColor() == BLUE) {
                if (player1Color == whoseMove) {
                    board.setFigure(x, y, new Figure(player1Color));
                    setWhoseMove();
                } else {
                    board.setFigure(x, y, new Figure(whoseMove));
                    setWhoseMove();
                }
                System.out.println("move: " + whoseMove + " " + new Field(x, y).toString());
                if (players == ONE_PLAYER) {
                    prepareMove(x, y);
                    doComputerMove(levelChoice);
                    showFigures();
                } else {
                    //TWO_PLAYERS
                    prepareMove(x, y);
                    showFigures();
                }
            }
        });

        backToStart.setOnAction(event -> {
            backToStart.setVisible(false);
            onePlayer.setVisible(false);
            twoPlayers.setVisible(false);
            basicLevel.setVisible(false);
            highLevel.setVisible(false);
            player1rName.setVisible(false);
            player2rName.setVisible(false);
            button1.setVisible(false);
            button2.setVisible(false);
            playAgain.setVisible(false);
            board = new Board();
            showBoard();
        });

        playAgain.setOnAction(event -> {
            playAgain.setVisible(false);
            board = new Board();
            showBoard();
        });
    }

    private int simpleMoveResult(int x, int y) {
        int retVal = 0;
        removeOldMarkers();
        flipScoredPawns(x, y);
        calculatePoints();
        if (whoseMove == BLACK) {
            retVal = participants.getPointsPlayer1();
        } else {
            retVal = participants.getPointsPlayer2();
        }
        return retVal;
    }

    //minimax and alpha-beta pruning
    private int minimax(Field pos, int depth, int alpha, int beta, boolean maxPlayer) {

        Child child;
        boolean gameOver = acceptableMoves.size() == 0;
        int retValue; //= simpleMoveResult(pos.getX(), pos.getY());

        System.out.println("                                              begin: MiniMax called for " + pos.toString() + "acceptableMoves.size() " + acceptableMoves.size() + " :" + whoseMove);

        if ((depth == 0) || gameOver) return simpleMoveResult(pos.getX(), pos.getY());
        if (maxPlayer) {
            retValue = MINUS_INFINITY;
            int maxEval = MINUS_INFINITY;
            prepareMove(pos.getX(), pos.getY());

            for (int i = 0; i < acceptableMoves.size(); i++) {
                child = new Child(acceptableMoves.get(i).getField());
                System.out.println("                                            if - child; i= " + i + "; " + child.getField().toString() + " - " + whoseMove);
                int eval = minimax(child.getField(), depth - 1, alpha, beta, false);
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                System.out.println("   if - (maxEval, alpha) " + "(" + maxEval + ", " + alpha + "); child.getField " + child.getField().toString());
                if (beta <= alpha) break;
                retValue = maxEval;
            }
        } else {
            retValue = PLUS_INFINITY;
            int minEval = PLUS_INFINITY;
            prepareMove(Child.field.getX(), Child.field.getY());
            System.out.println("                                            else - prepareMove " + Child.field.toString() + " - " + whoseMove);
            for (int i = 0; i < acceptableMoves.size(); i++) {
                System.out.println("                                        else - child; i= " + i + "; " + acceptableMoves.get(i).getField().toString() + " - " + whoseMove);
                int eval = minimax(acceptableMoves.get(i).getField(), depth - 1, alpha, beta, true);
                minEval = min(minEval, eval);
                beta = min(beta, eval);
                System.out.println("   else - (minEval, beta) " + "(" + minEval + ", " + beta + "); acceptableMove " + acceptableMoves.get(i).getField().toString());
                if (beta <= alpha) break;
                return minEval;
            }
        }
        return retValue;
    }

    private void setWhoseMove() {
        if (whoseMove == BLACK) {
            moveBlack.setVisible(false);
            moveWhite.setVisible(true);
        } else {
            moveBlack.setVisible(true);
            moveWhite.setVisible(false);
        }
    }

    private void prepareMove(int x, int y) {
        removeOldMarkers();
        flipScoredPawns(x, y);
        calculatePoints();
        switchPlayer();
        calculateMoves();
        setAcceptableMoves();
    }

    private void startPlay() {
        int selectBlack = (int) (Math.random() * 2 + 1);
        if (players == ONE_PLAYER) {
            button1.setOnAction(e -> {
                participants.setPlayer1(player1rName.getText());
                participants.setPlayer2("Computer");
                colorDrawInfo.setVisible(false);
                button1.setVisible(false);
                whoPlays.setVisible(false);
                player1rName.setVisible(false);

                if (selectBlack == 1) {
                    blackPlayerName.setText(participants.getPlayer1());
                    whitePlayerNme.setText(participants.getPlayer2());
                    player1Color = BLACK;
                    calculateMoves();
                    setAcceptableMoves();
                    moveBlack.setVisible(true);
                    moveWhite.setVisible(false);
                    System.out.println("HUMAN move " + whoseMove);
                } else {
                    blackPlayerName.setText(participants.getPlayer2());
                    whitePlayerNme.setText(participants.getPlayer1());
                    player1Color = WHITE;
                    calculateMoves();
                    setAcceptableMoves();
                    System.out.println("COMP move " + whoseMove);
                    doComputerMove(levelChoice);
                }
                showFigures();
            });
        } else if (players == TWO_PLAYERS) {
            //TWO_PLAYERS
            button1.setOnAction(event -> {
                participants.setPlayer1(player1rName.getText());
                player1rName.setVisible(false);
                button1.setVisible(false);
                player2rName.setVisible(true);
                button2.setVisible(true);
                player2rName.requestFocus();
            });
            button2.setOnAction(event -> {
                participants.setPlayer2(player2rName.getText());
                player2rName.setVisible(false);
                button2.setVisible(false);
                whoPlays.setVisible(false);
                colorDrawInfo.setVisible(false);

                if (selectBlack == 1) {
                    blackPlayerName.setText(participants.getPlayer1());
                    whitePlayerNme.setText(participants.getPlayer2());
                    player1Color = BLACK;
                    calculateMoves();
                    setAcceptableMoves();
                } else {
                    blackPlayerName.setText(participants.getPlayer2());
                    whitePlayerNme.setText(participants.getPlayer1());
                    player1Color = WHITE;
                    calculateMoves();
                    setAcceptableMoves();
                }
                showFigures();
                moveBlack.setVisible(true);
                moveWhite.setVisible(false);
            });
        }
        backToStart.setVisible(true);
    }

    private void flipScoredPawns(int x, int y) {
        List<Field> f = null;
        List<Field> toFlip = new ArrayList<>();
        for (int i = 0; i < acceptableMoves.size(); i++) {
            if (acceptableMoves.get(i).getField().getX() == x && acceptableMoves.get(i).getField().getY() == y) {
                f = acceptableMoves.get(i).getToScore();
                System.out.println("         *************** toFlip " + f.toString());
                for (int n = 0; n < f.size(); n++) {
                    toFlip.add(f.get(n));
                }
            }
        }
        for (int i = 0; i < toFlip.size(); i++) {
            gridPane.add(new ImageView(empty), toFlip.get(i).getX(), toFlip.get(i).getY());
            board.setFigure(toFlip.get(i).getX(), toFlip.get(i).getY(), new Figure(whoseMove));
        }
    }

    private void setAcceptableMoves() {
        for (int i = 0; i < acceptableMoves.size(); i++) {
            board.setFigure(acceptableMoves.get(i).getField().getX(), acceptableMoves.get(i).getField().getY(), new Figure(BLUE));
        }
    }

    private void removeOldMarkers() {
        for (int col = 4; col < 12; col++) {
            for (int row = 1; row < ROWS_NUMBER; row++) {
                Figure figure = board.getFigure(col, row);
                if (figure.getColor() == BLUE) {
                    board.setFigure(col, row, new Figure(NONE));
                    gridPane.add(new ImageView(empty), col, row);
                }
            }
        }
    }

    private void calculateMoves() {
        acceptableMoves = new ArrayList<>();
        List<Field> delta = new ArrayList<>();
        //start -> NE, clockwise
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
        Move m = new Move();
        int direction = 0;
        int addCounter = 0;

        for (int col = 4; col < 12; col++) {
            for (int row = 1; row < 9; row++) {
                if (board.getFigure(col, row).getColor() == NONE) {
                    Field tempField = new Field(col, row);
                    Field aux = tempField;
                    direction = 0;
                    do {
                        m.setField(tempField);
                        Field fAdd = aux.addField(delta.get(direction).getX(), delta.get(direction).getY());
                        boolean inBounds = (fAdd.getX() > 3) && (fAdd.getX() < 12)
                                && (fAdd.getY() > 0) && (fAdd.getY() < 9);
                        if (inBounds && (board.getFigure(fAdd.getX(), fAdd.getY()).getColor() == tempColor)) {
                            m.getToScore().add(fAdd);
                            addCounter++;
                            aux = fAdd;
                        } else if (inBounds && (addCounter > 0) && (board.getFigure(fAdd.getX(), fAdd.getY()).getColor() == whoseMove)) {
                            acceptableMoves.add(m);
                            m = new Move();
                            m.setField(tempField);
                            aux = tempField;
                            addCounter = 0;
                            direction++;
                        } else {
                            m = new Move();
                            m.setField(tempField);
                            aux = tempField;
                            addCounter = 0;
                            direction++;
                        }
                    } while (direction < delta.size());
                }
            }
        }

        List<Move> duplicates = new ArrayList<Move>();
        for (int i = 0; i < acceptableMoves.size() - 1; i++) {
            for (int j = i + 1; j < acceptableMoves.size(); j++) {
                if (acceptableMoves.get(i).equals(acceptableMoves.get(j))) {
                    duplicates.add(acceptableMoves.get(i));
                    duplicates.add(acceptableMoves.get(j));
                }
            }
        }

        for (int i = 0; i < duplicates.size(); i++) {
            List<Field> auxToScore = new ArrayList<>();
            for (int j = 0; j < acceptableMoves.size(); j++) {
                if (acceptableMoves.get(j).equals(duplicates.get(i))) {
                    auxToScore.addAll(acceptableMoves.get(j).getToScore());
                }
            }
            Set<Field> setToScore = new HashSet<>(auxToScore);
            auxToScore = new ArrayList<>(setToScore);
            duplicates.get(i).setToScore(auxToScore);
        }
        Set<Move> set = new HashSet<>(duplicates);
        duplicates = new ArrayList<Move>(set);

        for (int j = 0; j < duplicates.size(); j++) {
            int loopCounter = acceptableMoves.size();
            for (int i = 0; i < loopCounter; i++) {
                if (acceptableMoves.get(i).equals(duplicates.get(j))) {
                    acceptableMoves.remove(acceptableMoves.get(i));
                    loopCounter--;
                }
            }
        }

        acceptableMoves.addAll(duplicates);
        Set<Move> sm = new HashSet<>(acceptableMoves);
        acceptableMoves = new ArrayList<>(sm);

        for (int i = 0; i < acceptableMoves.size(); i++) {
            System.out.println(acceptableMoves.get(i).getField().toString());
        }

        if (acceptableMoves.size() == 0) {
            System.out.println("End of the game - no move for " + whoseMove);
            moveBlack.setVisible(false);
            moveWhite.setVisible(false);
            infoBasicOrHigh.setVisible(false);
            infoLevel.setVisible(false);
            playAgain.setVisible(true);
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
        System.out.println("Result B : W - " + b + " : " + w);
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

    private void doComputerMove(boolean isHighLevelSet) {
        List<Field> computerMoves = new ArrayList<>();

        for (int i = 0; i < acceptableMoves.size(); i++) {
            computerMoves.add(acceptableMoves.get(i).getField());
        }

        System.out.println(whoseMove + "==>> doComputerMove() ==>>>");
        for (int i = 0; i < computerMoves.size(); i++) {
            System.out.println(computerMoves.get(i).toString());
        }

        if (computerMoves.size() > 0) {
            if (!isHighLevelSet) {
                int random = (int) (random() * computerMoves.size());
                Field toField = computerMoves.get(random);
                board.setFigure(toField.getX(), toField.getY(), new Figure(whoseMove));
                setWhoseMove();
                System.out.println(whoseMove + "==>> doComputerMove() ==>> " + "[" + toField.getX() + ", "
                        + toField.getY() + "]" + " computerMoves.size = " + computerMoves.size()
                        + "; " + "random = " + random + "; " + whoseMove + " move ");

                prepareMove(toField.getX(), toField.getY());
            } else {
                //do MiniMax alpha-beta pruning algorithm
                Evaluation evaluation = new Evaluation();
                ArrayList<Evaluation> tree = new ArrayList<>();

                for (int i = 0; i < acceptableMoves.size(); i++) {
                    Field f = acceptableMoves.get(i).getField();
                    System.out.println("                                (697)evaluation.setPosition(f) " + i + "; f: " + f.toString() + "; computerMoves.size() " + computerMoves.size());
                    evaluation.setPosition(f);
                    evaluation.setEvaluation(minimax(f, 4, MINUS_INFINITY, PLUS_INFINITY, true));
                    tree.add(evaluation);
                }
                Evaluation moveThere = tree.get(0);
                for (int i = 1; i < tree.size(); i++) {
                    if (moveThere.getEvaluation() < tree.get(i).getEvaluation()) {
                        moveThere = tree.get(i);
                    }
                }
                prepareMove(moveThere.getPosition().getX(), moveThere.getPosition().getY());
                System.out.println("                            (709)Best evaluated move for computer:" + whoseMove + ": [" + moveThere.getPosition().getX() + ", " + moveThere.getPosition().getY() + "]");
            }
        } else {
            System.out.println("End of this game - no move for COMPUTER");
        }
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

    private Label addInfo(String s) {
        Label l = new Label(s);
        l.setMaxWidth(75);
        l.setMaxHeight(35);
        l.setPadding(new Insets(3, 0, 0, 0));
        l.setFont(new Font("Arial", 20));
        l.setTextFill(Color.web("#F00"));
        l.setAlignment(Pos.CENTER);
        l.setVisible(false);
        return l;
    }
}
