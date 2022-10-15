package net;

import static java.net.URLEncoder.*;
import static java.nio.charset.StandardCharsets.*;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.stream.Collectors;

public class FormRequest extends Request {

	private static final String FORM_URLENCODED = "application/x-www-form-urlencoded";

	public FormRequest(String url) throws URISyntaxException {
		super(url);
	}

	@Override
	public Response send(Map<String, String> body) throws Exception {
		final HttpClient httpClient = HttpClient.newHttpClient();
		final HttpRequest request = buildRequest(body);

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return new Response(response);
	}

	private HttpRequest buildRequest(Map<String, String> body) {
		return HttpRequest.newBuilder()
			.uri(url)
			.header("Content-Type", FORM_URLENCODED)
			.POST(HttpRequest.BodyPublishers.ofString(getEncodedBody(body)))
			.build();
	}

	private String getEncodedBody(Map<String, String> params) {
		return params.entrySet().stream()
			.map(this::joinEntry)
			.collect(Collectors.joining("&"));
	}

	private String joinEntry(Map.Entry<String, String> entry) {
		return String.join("=", encode(entry.getKey(), UTF_8), encode(entry.getValue(), UTF_8));
	}
}
