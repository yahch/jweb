package com.tfsis.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bigtesting.routd.Route;
import org.bigtesting.routd.Router;
import org.bigtesting.routd.TreeRouter;

import com.tfsis.server.handlers.HttpHandlerBase;
import com.tfsis.server.responses.NotFoundResponse;
import com.tfsis.server.responses.StatusResponse;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class AppServer extends NanoHTTPD {

	java.util.logging.Logger log = java.util.logging.Logger.getLogger("AppServer");

	public AppServer(int port) throws IOException {
		super(port);
	}

	private Router router;

	private HashMap<Route, HttpHandlerBase> routers;

	public void registeRouters(HashMap<String, HttpHandlerBase> r) {
		router = new TreeRouter();
		routers = new HashMap<>();

		for (Entry<String, HttpHandlerBase> entry : r.entrySet()) {
			Route rt = new Route(entry.getKey());
			router.add(rt);
			routers.put(rt, entry.getValue());
		}
	}

	// private void initRoutersMap() {
	// routers.put(new Route("/"), new HomeHandler());
	// routers.put(new Route("/page/:id<[0-9]+>"), new HomeHandler());
	// routers.put(new Route("/post/:id<[0-9]+>"), new HomeHandler());
	// routers.put(new Route("/scripts/:name"), new HomeHandler());
	// routers.put(new Route("/styles/:name"), new HomeHandler());
	// routers.put(new Route("/fonts/:name"), new HomeHandler());
	// }

	@Override
	public Response serve(IHTTPSession session) {
		String urlPath = session.getUri();
		Route route = router.route(urlPath);
		if (route == null) {
			return new NotFoundResponse();
		}
		if (!routers.containsKey(route)) {
			return new NotFoundResponse();
		}
		HttpHandlerBase handler = routers.get(route);
		handler.setSessionContext(session);
		handler.setSessionRoute(route);
		try {
			return handler.process();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			return new StatusResponse(Status.INTERNAL_ERROR);
		}
	}

	@Override
	public void start(int arg0, boolean arg1) throws IOException {
		log.log(Level.INFO, "starting...");
		super.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
	}
}