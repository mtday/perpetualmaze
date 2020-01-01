package com.perpetualmaze.store;

import com.perpetualmaze.core.HighScore;

import java.util.Optional;

public interface HighScoreStore {
    Optional<HighScore> get();

    void save(HighScore highScore);
}
