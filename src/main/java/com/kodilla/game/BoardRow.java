package com.kodilla.game;

import java.util.ArrayList;
import java.util.List;

public class BoardRow {
    private List<Figure> cols = new ArrayList<>();

    public BoardRow() {
        for (int n = 0; n < 8; n++)
            cols.add(new Figure(FigureColor.NONE));
    }

    public List<Figure> getCols() {
        return cols;
    }
}
