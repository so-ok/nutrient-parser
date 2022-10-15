package resolver;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import product.Product;

class ResolverTest {

	private static final Product PRODUCT_TERA_SLEEP = new Product(2022021000416566L, 200400200082583L,
		"수면엔 테라슬립 락티움 제트 솔루션 ZZZ", "20221014", "주식회사 노바렉스");

	@Test
	void resolve_products_from_response() {
		JsonArrayResolver<Product> productResolver = new JsonArrayResolver<>(SampleData.PRODUCTS_JSON.getData(), Product.class);
		List<Product> products = productResolver.getList();

		assertThat(products).contains(PRODUCT_TERA_SLEEP);
	}
}