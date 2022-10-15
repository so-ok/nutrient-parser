package net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public abstract class Request {
	protected final URI url;

	public Request(String url) throws URISyntaxException {
		this.url = new URI(url);
	}

	public abstract Response send(Map<String, String> body) throws Exception;
}
