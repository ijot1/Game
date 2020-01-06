package com.kodilla.game;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.othello.OthelloApp.ROWS_NUMBER;

public class Board {

    private String info;
    private String prompt;
    private String player1;
    private String player2;
    private int pointsPlayer1;
    private int pointsPlayer2;

    private List<BoardRow> rows = new ArrayList<>();

    public Board() {
        player1 = "";
        player2 = "";
        pointsPlayer1 = 0;
        pointsPlayer2 = 0;

        for (int n = 0; n < ROWS_NUMBER; n++)
            rows.add(new BoardRow());
    }

    public Figure getFigure(int col, int row) {
        return rows.get(row).getCols().get(col);
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getPointsPlayer1() {
        return pointsPlayer1;
    }

    public int getPointsPlayer2() {
        return pointsPlayer2;
    }

    public void setFigure(int col, int row, Figure figure) {
        rows.get(row).getCols().add(col, figure);
        rows.get(row).getCols().remove(col + 1);
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void setPointsPlayer1(int pointsPlayer1) {
        this.pointsPlayer1 = pointsPlayer1;
    }

    public void setPointsPlayer2(int pointsPlayer2) {
        this.pointsPlayer2 = pointsPlayer2;
    }
}
