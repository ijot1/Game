package com.kodilla.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Move {

    private Field field;
    //    private HashSet<Field> toScore = new HashSet<>();    //HashSet
    private List<Field> toScore = new ArrayList<>();    //HashSet

    public Field getField() {
        return field;
    }

    public List<Field> getToScore() {
        return toScore;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setToScore(List<Field> toScore) {
        this.toScore = toScore;
    }

    @Override
    public String toString() {
        return "Move{" +
                "field=" + field +
                ", toScore=" + toScore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return getField().equals(move.getField());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField());
    }
}


