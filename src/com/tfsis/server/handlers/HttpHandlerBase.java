package com.tfsis.server.handlers;

import org.bigtesting.routd.Route;

import com.tfsis.server.requests.HttpRequest;
import com.tfsis.server.responses.HttpResponseBase;

import fi.iki.elonen.NanoHTTPD;

public abstract class HttpHandlerBase {

	protected HttpRequest request;

	protected Route sessionRoute;

	public void setSessionContext(NanoHTTPD.IHTTPSession sessionContext) {
		this.request = new HttpRequest(sessionContext);
	}

	public void setSessionRoute(Route sessionRoute) {
		this.sessionRoute = sessionRoute;
	}

	public abstract HttpResponseBase process();

}
