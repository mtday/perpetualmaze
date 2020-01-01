package com.perpetualmaze.app.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

public class AccessLogFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        LOGGER.info("{} {}", requestContext.getMethod(), requestContext.getUriInfo().getPath());
    }
}
