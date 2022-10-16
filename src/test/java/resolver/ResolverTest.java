package resolver;

import static domain.Ingredient.Category.*;
import static org.assertj.core.api.Assertions.*;
import static resolver.SampleData.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import domain.Ingredient;
import domain.Product;

class ResolverTest {

	private static final Product PRODUCT_TERA_SLEEP = new Product(2022021000416566L, 200400200082583L,
		"수면엔 테라슬립 락티움 제트 솔루션 ZZZ", "20221014", "주식회사 노바렉스");

	private static final List<String> TERA_SLEEP_FUNCTIONAL_INGREDIENT_NAMES = List.of("L-테아닌", "비타민 B6", "유단백가수분해물");

	@Test
	void resolve_products_from_response() {
		JsonArrayResolver<Product> resolver = new JsonArrayResolver<>(PRODUCTS_JSON.getData(), Product.class);
		List<Product> products = resolver.getList();

		assertThat(products).contains(PRODUCT_TERA_SLEEP);
	}

	@Test
	void resolve_ingredients_from_response() {
		JsonArrayResolver<Ingredient> resolver = new JsonArrayResolver<>(INGREDIENTS_JSON.getData(), Ingredient.class);
		List<Ingredient> functionalIngredients = filterFunctionalIngredients(resolver.getList());

		assertThat(functionalIngredients).allSatisfy(
			ingredient -> assertThat(TERA_SLEEP_FUNCTIONAL_INGREDIENT_NAMES.contains(ingredient.getName())).isTrue());
	}

	private List<Ingredient> filterFunctionalIngredients(List<Ingredient> ingredients) {
		return ingredients.stream()
			.filter(ingredient -> ingredient.hasCategory(FUNCTIONAL))
			.collect(Collectors.toList());
	}
}