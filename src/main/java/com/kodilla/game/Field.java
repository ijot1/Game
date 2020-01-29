package com.kodilla.game;

import java.util.Objects;

public class Field {
    private int x;
    private int y;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Field addField(int dx, int dy) {
        return new Field(x + dx, y + dy);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field)) return false;
        Field field = (Field) o;
        return getX() == field.getX() &&
                getY() == field.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
