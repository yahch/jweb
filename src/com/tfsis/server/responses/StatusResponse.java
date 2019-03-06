package com.tfsis.server.responses;

import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StatusResponse extends HttpResponseBase {

    public StatusResponse(IStatus status) {
        super(status, NanoHTTPD.MIME_HTML, new ByteArrayInputStream(new byte[0]), 0L);
    }

    public StatusResponse(IStatus status, String mimeType, InputStream data, long totalBytes) {
        super(status, mimeType, data, totalBytes);
    }
}
