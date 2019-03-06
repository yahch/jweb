package com.tfsis.server.responses;

import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD.Response;

public abstract class HttpResponseBase extends Response {

	protected HttpResponseBase(IStatus status, String mimeType, InputStream data, long totalBytes) {
		super(status, mimeType, data, totalBytes);
	}
}
