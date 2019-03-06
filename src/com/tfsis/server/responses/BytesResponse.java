package com.tfsis.server.responses;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BytesResponse extends HttpResponseBase {

    public BytesResponse(byte[] data, String mime) {
        super(Status.OK, mime, new ByteArrayInputStream(data), (long) data.length);
    }

    public BytesResponse(IStatus status, byte[] data, String mime) {
        super(status, mime, new ByteArrayInputStream(data), (long) data.length);
    }

    public BytesResponse(IStatus status, String mimeType, InputStream data, long totalBytes) {
        super(status, mimeType, data, totalBytes);
    }

}
