package com.perpetualmaze.app.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

public class ErrorExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorExceptionMapper.class);

    private final UriInfo uriInfo;

    @Inject
    public ErrorExceptionMapper(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @Override
    public Response toResponse(Throwable throwable) {
        String path = uriInfo.getAbsolutePath().getPath();

        Error error = new Error();
        if (throwable instanceof NotFoundException) {
            LOGGER.error("Not Found: {}", path);
            error.setCode(NOT_FOUND.getStatusCode());
            error.setMessage(throwable.getMessage());
        } else if (throwable instanceof WebApplicationException) {
            LOGGER.error("Exception caught", throwable);
            WebApplicationException wae = (WebApplicationException) throwable;
            error.setCode(wae.getResponse().getStatus());
            error.setMessage(wae.getMessage());
        } else {
            LOGGER.error("Exception caught", throwable);
            error.setCode(INTERNAL_SERVER_ERROR.getStatusCode());
            error.setMessage(throwable.getMessage());
        }

        return status(error.getCode()).entity(error).type(APPLICATION_JSON).build();
    }

    private static class Error {
        private int code;
        private String message;

        public Error() {
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
