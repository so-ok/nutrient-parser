package product;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class ProductResolverTest {

	private static final String SAMPLE_PRODUCT_JSON = "[{\"prdlst_report_ledg_no\":\"2022021000416566\",\"prdlst_nm\":\"수면엔 테라슬립 락티움 제트 솔루션 ZZZ\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"200400200082583\",\"bssh_nm\":\"주식회사 노바렉스\",\"total_count\":\"31874\",\"no\":\"1\"},{\"prdlst_report_ledg_no\":\"2022021000416230\",\"prdlst_nm\":\"메디폴리스(Medipolis)\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"2015001201354\",\"bssh_nm\":\"유니크바이오텍(주)\",\"total_count\":\"31874\",\"no\":\"2\"},{\"prdlst_report_ledg_no\":\"2022021000416989\",\"prdlst_nm\":\"Aqua snow white rose(전량수출용)\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"200400200023261\",\"bssh_nm\":\"코스맥스바이오(주)\",\"total_count\":\"31874\",\"no\":\"3\"},{\"prdlst_report_ledg_no\":\"2022021000416998\",\"prdlst_nm\":\"기억력 업 플러스(Memory Up Plus)\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"2020001605265\",\"bssh_nm\":\"주식회사 상상바이오\",\"total_count\":\"31874\",\"no\":\"4\"},{\"prdlst_report_ledg_no\":\"2022021000417000\",\"prdlst_nm\":\"닥터에스더 어린콜라겐 비오틴 플러스\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"202000162024\",\"bssh_nm\":\"주식회사 바이오360\",\"total_count\":\"31874\",\"no\":\"5\"},{\"prdlst_report_ledg_no\":\"2022021000416999\",\"prdlst_nm\":\"더힘찬 면역 홍삼정 23\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"20040020031430\",\"bssh_nm\":\"대동고려삼(주)\",\"total_count\":\"31874\",\"no\":\"6\"},{\"prdlst_report_ledg_no\":\"2022021000417191\",\"prdlst_nm\":\"맥스(MAX) 4,200\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"200400200201066\",\"bssh_nm\":\"엠에스바이오텍(주)\",\"total_count\":\"31874\",\"no\":\"7\"},{\"prdlst_report_ledg_no\":\"2022021000416997\",\"prdlst_nm\":\"부광마그빈\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"200400200023263\",\"bssh_nm\":\"코스맥스바이오(주)\",\"total_count\":\"31874\",\"no\":\"8\"},{\"prdlst_report_ledg_no\":\"2022021000417189\",\"prdlst_nm\":\"데일리와이케어 유산균\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"200400200201065\",\"bssh_nm\":\"엠에스바이오텍(주)\",\"total_count\":\"31874\",\"no\":\"9\"},{\"prdlst_report_ledg_no\":\"2022021000416992\",\"prdlst_nm\":\"슈프리다이어트프로바이오틱스(SUPRE DIET PROBIOTICS)\",\"prms_dt\":\"20221014\",\"prdlst_report_no\":\"200400200201064\",\"bssh_nm\":\"엠에스바이오텍(주)\",\"total_count\":\"31874\",\"no\":\"10\"}]";

	private static final Product PRODUCT_TERA_SLEEP = new Product(2022021000416566L, 200400200082583L,
		"수면엔 테라슬립 락티움 제트 솔루션 ZZZ", "20221014", "주식회사 노바렉스");

	@Test
	void resolve_from_response() {
		JsonArrayResolver<Product> productResolver = new JsonArrayResolver<>(SAMPLE_PRODUCT_JSON, Product.class);
		List<Product> products = productResolver.getList();

		assertThat(products).contains(PRODUCT_TERA_SLEEP);
	}
}