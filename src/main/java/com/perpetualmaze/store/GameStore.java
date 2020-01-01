package com.perpetualmaze.store;

import com.perpetualmaze.core.Game;

import java.util.Optional;

public interface GameStore {
    Optional<Game> get(String id);

    void save(Game game);

    void delete(String id);
}
