package com.perpetualmaze.app.rest.highscore;

import com.perpetualmaze.store.HighScoreStore;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;

@Singleton
@Path("/highscore")
@Produces(APPLICATION_JSON)
public class GetHighScore {
    private final HighScoreStore highScoreStore;

    @Inject
    public GetHighScore(HighScoreStore highScoreStore) {
        this.highScoreStore = highScoreStore;
    }

    @GET
    public Response get() {
        return highScoreStore.get()
                .map(highScore -> ok().entity(highScore).type(APPLICATION_JSON).build())
                .orElseThrow(() -> new NotFoundException("High Score not found"));
    }
}
