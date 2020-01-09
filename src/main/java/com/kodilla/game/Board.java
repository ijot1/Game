package com.kodilla.game;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.othello.OthelloApp.ROWS_NUMBER;

public class Board {

    private List<BoardRow> rows = new ArrayList<>();

    public Board() {
        for (int n = 0; n < ROWS_NUMBER; n++)
            rows.add(new BoardRow());
    }

    public Figure getFigure(int col, int row) {
        return rows.get(row).getCols().get(col);
    }

    public void setFigure(int col, int row, Figure figure) {
        rows.get(row).getCols().add(col, figure);
        rows.get(row).getCols().remove(col + 1);
    }
}
