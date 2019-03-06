package com.tfsis.server.responses;

import fi.iki.elonen.NanoHTTPD;

import java.io.InputStream;

public class StreamResponse extends HttpResponseBase {

    public StreamResponse(String mimeType, InputStream data) {
        super(Status.OK, mimeType, data, -1L);
    }

    public StreamResponse(IStatus status, String mimeType, InputStream data, long totalBytes) {
        super(status, mimeType, data, -1L);
    }
}
