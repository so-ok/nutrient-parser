package net;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class FormRequestTest {

	@Test
	void send_post_request() throws Exception {
		Request request = new FormRequest(
			"https://www.foodsafetykorea.go.kr/portal/healthyfoodlife/searchHomeHFDetail.do");
		Map<String, String> body = Map.of("prdlstReportLedgNo", "2022021000416566");
		Response response = request.send(body);

		assertThat(response.getBody()).contains("노바렉스");
	}
}
