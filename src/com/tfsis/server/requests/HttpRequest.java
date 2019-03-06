package com.tfsis.server.requests;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;

public class HttpRequest {

	private IHTTPSession sessionContext;

	public HttpRequest(IHTTPSession session) {
		this.sessionContext = session;
	}
}
