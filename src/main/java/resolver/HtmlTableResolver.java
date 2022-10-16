package resolver;

import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlTableResolver {

	private final String body;

	public HtmlTableResolver(String body) {
		this.body = body;
	}

	public Map<String, String> resolve(String root) {
		Document document = Jsoup.parse(body);
		Elements rootElement = document.select(root);
		Elements tables = rootElement.select("table:first-child");
		return generateMapFromTable(tables);
	}

	private Map<String, String> generateMapFromTable(Elements tables) {
		Elements rows = tables.select("tbody > tr");
		return rows.stream()
			.collect(Collectors.toMap(
				row -> row.select("th").text(),
				row -> row.select("td").text()));
	}
}
