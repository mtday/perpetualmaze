package com.perpetualmaze;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.perpetualmaze.app.Application;

import static java.util.Arrays.stream;
import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        int port = stream(args).map(Integer::parseInt).findFirst().orElse(8080);
        Server server = new Server(port);

        ServletContextHandler contextHandler = new ServletContextHandler(NO_SESSIONS);
        contextHandler.setContextPath("/");
        server.setHandler(contextHandler);

        ServletHolder servletHolder = contextHandler.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(1);
        servletHolder.setInitParameter("javax.ws.rs.Application", Application.class.getName());

        try {
            server.start();
            server.join();
        } catch (Exception exception) {
            LOGGER.error("Jetty server failed", exception);
        } finally {
            server.destroy();
        }
    }
}
