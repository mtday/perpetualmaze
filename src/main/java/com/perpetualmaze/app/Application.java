package com.perpetualmaze.app;

import org.glassfish.jersey.server.ResourceConfig;
import com.perpetualmaze.app.filter.AccessLogFilter;
import com.perpetualmaze.app.rest.ErrorExceptionMapper;
import com.perpetualmaze.app.rest.game.CreateGame;
import com.perpetualmaze.app.rest.game.GetGame;
import com.perpetualmaze.app.rest.highscore.GetHighScore;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class Application extends ResourceConfig {
    public Application() {
        // High Scores
        register(GetHighScore.class);

        // Games
        register(CreateGame.class);
        register(GetGame.class);

        // Errors
        register(ErrorExceptionMapper.class);

        // Filters
        register(AccessLogFilter.class);

        // Dependency Injection
        register(new DependencyInjectionBinder());
    }
}
