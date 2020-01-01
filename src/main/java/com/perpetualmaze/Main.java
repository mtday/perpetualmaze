package com.perpetualmaze;

import com.perpetualmaze.app.Application;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws URISyntaxException, MalformedURLException {
        int port = stream(args).map(Integer::parseInt).findFirst().orElse(8080);
        Server server = new Server(port);

        ServletContextHandler contextHandler = new ServletContextHandler(NO_SESSIONS);
        contextHandler.setContextPath("/");

        URL indexUrl = ofNullable(Main.class.getClassLoader().getResource("web/index.html"))
                .orElseThrow(() -> new RuntimeException("Failed to find index.html"));
        URI baseUri = indexUrl.toURI().resolve("./");
        LOGGER.info("Base URI: {}", baseUri);
        contextHandler.setBaseResource(Resource.newResource(baseUri));

        ServletHolder servletHolder = contextHandler.addServlet(ServletContainer.class, "/api/*");
        servletHolder.setInitOrder(1);
        servletHolder.setInitParameter("javax.ws.rs.Application", Application.class.getName());

        ServletHolder staticHolder = new ServletHolder("default", DefaultServlet.class);
        contextHandler.addServlet(staticHolder, "/");

        HandlerList handlerList = new HandlerList();
        handlerList.addHandler(contextHandler);
        handlerList.addHandler(new DefaultHandler());
        server.setHandler(handlerList);

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
