package com.kodilla.game;

public class Evaluation {
    private static Field position;
    private static int evaluation;

    public static Field getPosition() {
        return position;
    }

    public static void setPosition(Field position) {
        Evaluation.position = position;
    }

    public static int getEvaluation() {
        return evaluation;
    }

    public static void setEvaluation(int evaluation) {
        Evaluation.evaluation = evaluation;
    }
}
