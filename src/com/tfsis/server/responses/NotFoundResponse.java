package com.tfsis.server.responses;

public class NotFoundResponse extends StatusResponse {

    public NotFoundResponse() {
        super(Status.NOT_FOUND);
    }

    public NotFoundResponse(Status status) {
        super(status);
    }
}
