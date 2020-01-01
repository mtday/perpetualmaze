package com.perpetualmaze.app.rest.game;

import com.perpetualmaze.core.Board;
import com.perpetualmaze.core.Game;
import com.perpetualmaze.core.Piece;
import com.perpetualmaze.core.Uid;
import com.perpetualmaze.store.GameStore;
import com.perpetualmaze.store.PieceStore;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;

@Singleton
@Path("/game/create")
@Produces(APPLICATION_JSON)
public class CreateGame {
    private final GameStore gameStore;
    private final PieceStore pieceStore;

    @Inject
    public CreateGame(GameStore gameStore, PieceStore pieceStore) {
        this.gameStore = gameStore;
        this.pieceStore = pieceStore;
    }

    @GET
    public Response get() {
        String id = Uid.randomUid();
        Board board = new Board(15, 15);
        Piece piece1 = pieceStore.random();
        Piece piece2 = pieceStore.random();
        Piece piece3 = pieceStore.random();
        Game game = new Game(id, 0, board, piece1, piece2, piece3);

        gameStore.save(game);

        return ok().entity(game).type(APPLICATION_JSON).build();
    }
}
