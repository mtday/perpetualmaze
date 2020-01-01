package com.perpetualmaze.app;

import com.perpetualmaze.app.filter.AccessLogFilter;
import com.perpetualmaze.app.rest.ErrorExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class Application extends ResourceConfig {
    public Application() {
        // Errors
        register(ErrorExceptionMapper.class);

        // Filters
        register(AccessLogFilter.class);

        // Dependency Injection
        register(new DependencyInjectionBinder());
    }
}
