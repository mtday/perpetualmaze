package com.perpetualmaze.core;

public class Game {
    private final String id;
    private final int score;
    private final Board board;
    private final Piece piece1;
    private final Piece piece2;
    private final Piece piece3;

    public Game(String id, int score, Board board, Piece piece1, Piece piece2, Piece piece3) {
        this.id = id;
        this.score = score;
        this.board = board;
        this.piece1 = piece1;
        this.piece2 = piece2;
        this.piece3 = piece3;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Board getBoard() {
        return board;
    }

    public Piece getPiece1() {
        return piece1;
    }

    public Piece getPiece2() {
        return piece2;
    }

    public Piece getPiece3() {
        return piece3;
    }
}
