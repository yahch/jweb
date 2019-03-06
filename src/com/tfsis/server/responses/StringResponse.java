package com.tfsis.server.responses;

import fi.iki.elonen.NanoHTTPD;

import java.nio.charset.Charset;

public class StringResponse extends BytesResponse {

    public StringResponse(String text) {
        super(text.getBytes(Charset.forName("utf-8")), NanoHTTPD.MIME_HTML);
    }

    public StringResponse(String text, String mime) {
        super(text.getBytes(Charset.forName("utf-8")), mime);
    }

    public StringResponse(IStatus status, String text, String mime) {
        super(status, text.getBytes(Charset.forName("utf-8")), mime);
    }

    public StringResponse(byte[] data, String mime) {
        super(data, mime);
    }
}
