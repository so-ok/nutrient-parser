import java.util.Map;

import net.FormRequest;
import net.Request;
import net.Response;

public class Sender {

	private static final String PRODUCT_URL = "https://www.foodsafetykorea.go.kr/portal/healthyfoodlife/searchHomeHFProc.do";
	private static final String INGREDIENT_URL = "https://www.foodsafetykorea.go.kr/portal/healthyfoodlife/searchHfPrdlstRawmtrl.do";
	private static final String INFORMATION_URL = "https://www.foodsafetykorea.go.kr/portal/healthyfoodlife/searchHomeHFDetail.do";

	public Response retrieveProduct(int startIndex, int pageSize) throws Exception {
		Request request = new FormRequest(PRODUCT_URL);
		Map<String, String> body = Map.ofEntries(
			Map.entry("start_idx", String.valueOf(startIndex)),
			Map.entry("show_cnt", String.valueOf(pageSize))
		);
		return request.send(body);
	}

	public Response retrieveIngredient(Long reportNumber) throws Exception {
		Request request = new FormRequest(INGREDIENT_URL);
		Map<String, String> body = Map.of("prdlst_report_no", String.valueOf(reportNumber));
		return request.send(body);
	}

	public Response retrieveInformation(Long ledgerNumber) throws Exception {
		Request request = new FormRequest(INFORMATION_URL);
		Map<String, String> body = Map.of("prdlstReportLedgNo", String.valueOf(ledgerNumber));
		return request.send(body);
	}
}
