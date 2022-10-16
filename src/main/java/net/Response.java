package net;

import java.net.http.HttpResponse;

public class Response {

	private final int status;
	private final String body;

	public Response(int status, String body) {
		this.status = status;
		this.body = body;
	}

	public Response(HttpResponse<String> response) {
		this(response.statusCode(), response.body());
	}

	public int getStatus() {
		return status;
	}

	public String getBody() {
		return body;
	}
}
