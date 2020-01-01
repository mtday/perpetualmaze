package com.perpetualmaze.store;

import com.perpetualmaze.core.Piece;

import java.util.List;

public interface PieceStore {
    List<Piece> getAll();

    Piece random();
}
