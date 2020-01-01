package com.perpetualmaze.app.rest.maze;

import com.perpetualmaze.generator.MazeGenerator;
import com.perpetualmaze.model.Maze;
import com.perpetualmaze.model.MazeId;
import com.perpetualmaze.store.MazeStore;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Singleton
@Path("/maze/{level}")
@Produces(APPLICATION_JSON)
public class GetMaze {
    private final MazeStore mazeStore;

    @Inject
    public GetMaze(MazeStore mazeStore) {
        this.mazeStore = mazeStore;
    }

    @GET
    public Response get(@PathParam("level") int level,
                        @QueryParam("width") @DefaultValue("200") int width,
                        @QueryParam("height") @DefaultValue("200") int height) {
        // return the existing maze if it exists in the store
        MazeId id = new MazeId().setLevel(level).setWidth(width).setHeight(height);
        Optional<Maze> existing = mazeStore.get(id);
        if (existing.isPresent()) {
            return Response.ok().entity(existing.get()).type(APPLICATION_JSON).build();
        }

        if (level == 1) {
            // create a new top-level maze
            Maze topLevelMaze = MazeGenerator.createMaze(width, height);
            mazeStore.save(topLevelMaze);
            return Response.ok().entity(topLevelMaze).type(APPLICATION_JSON).build();
        }

        // create a new maze if the previous maze can be found in the store
        MazeId previousId = new MazeId().setLevel(level - 1).setWidth(width).setHeight(height);
        Optional<Maze> previous = mazeStore.get(previousId);
        if (previous.isPresent()) {
            Maze newMaze = MazeGenerator.createMaze(previous.get());
            mazeStore.save(newMaze);
            return Response.ok().entity(newMaze).type(APPLICATION_JSON).build();
        }

        // no previous maze available, so unable to create new maze
        throw new WebApplicationException("Invalid maze level: " + level);
    }
}
