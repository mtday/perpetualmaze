package com.perpetualmaze.app.rest.game;

import com.perpetualmaze.store.GameStore;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;

@Singleton
@Path("/game/id/{id}")
@Produces(APPLICATION_JSON)
public class GetGame {
    private final GameStore gameStore;

    @Inject
    public GetGame(GameStore gameStore) {
        this.gameStore = gameStore;
    }

    @GET
    public Response get(@PathParam("id") String id) {
        return gameStore.get(id)
                .map(game -> ok().entity(game).type(APPLICATION_JSON).build())
                .orElseThrow(() -> new NotFoundException("Game with id " + id + " not found"));
    }
}
