package com.kodilla.game;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.othello.OthelloApp.COLS_NUMBER;

public class BoardRow {

    private List<Figure> cols = new ArrayList<>();

    public BoardRow() {
        for (int n = 0; n < COLS_NUMBER; n++)
            cols.add(new Figure(FigureColor.NONE));
    }

    public List<Figure> getCols() {
        return cols;
    }
}
