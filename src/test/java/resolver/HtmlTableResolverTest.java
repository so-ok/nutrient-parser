package resolver;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import net.FormRequest;
import net.Request;
import net.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Information;
import domain.InformationMapAdapter;

class HtmlTableResolverTest {

	public static final String FOODSAFETYKOREA_URL = "https://www.foodsafetykorea.go.kr/portal/healthyfoodlife/searchHomeHFDetail.do";
	public static final String TERA_SLEEP_LEDGER_NUMBER = "2022021000416566";

	@Test
	void resolve_information() throws Exception {
		Response response = getResponse();
		HtmlTableResolver htmlTableResolver = new HtmlTableResolver(response.getBody());
		Map<String, String> map = htmlTableResolver.resolve(".page-container");

		Information information = new InformationMapAdapter(map);
		printMap(map);

		ObjectMapper mapper = new ObjectMapper();

		assertThat(mapper.writeValueAsString(information)).doesNotContain("null");
		assertThat(mapper.valueToTree(information).size()).isEqualTo(13);
	}

	private Response getResponse() throws Exception {
		Request request = new FormRequest(FOODSAFETYKOREA_URL);
		Map<String, String> body = Map.of("prdlstReportLedgNo", TERA_SLEEP_LEDGER_NUMBER);
		return request.send(body);
	}

	private void printMap(Map<String, String> map) {
		int i = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("(" + (++i) + ") " + entry.getKey() + " => " + entry.getValue());
		}
	}
}