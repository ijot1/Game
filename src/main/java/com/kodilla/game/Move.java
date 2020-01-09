package com.kodilla.game;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private Field field;
    private List<Field> toScore = new ArrayList<>();

    public Field getField() {
        return field;
    }

    public List<Field> getToScore() {
        return toScore;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
