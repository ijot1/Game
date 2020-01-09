package com.kodilla.game;

public class Field {
    private int x;
    private int y;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

        public int getX() {
        return x;
    }

    public Field addField (Field f) {
        Field field = new Field(x += f.getX(), y += f.getY());
        return field;
    }

    public int getY() {
        return y;
    }
}
