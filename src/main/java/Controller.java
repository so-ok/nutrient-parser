import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.Response;

import domain.Information;
import domain.InformationMapAdapter;
import domain.Ingredient;
import domain.Product;
import resolver.HtmlTableResolver;
import resolver.JsonArrayResolver;

public class Controller {

	private static final Sender sender = new Sender();

	public static void main(String[] args) throws Exception {
		List<Product> products = new ArrayList<>();
		Map<Product, List<Ingredient>> ingredients = new HashMap<>();
		Map<Product, Information> informations = new HashMap<>();

		int pageSize = 5;
		int totalItemCount = 10;
		for (int i = 1, page = 1; i <= totalItemCount; i += pageSize, page++) {
			List<Product> resolvedProducts = getProducts(page, pageSize);
			products.addAll(resolvedProducts);
			int aaaaa = 1;
			for (Product product : resolvedProducts) {

				List<Ingredient> resolvedIngredients = getIngredientsFor(product);
				ingredients.put(product, resolvedIngredients);

				Information information = getInformationFor(product);
				informations.put(product, information);

				System.out.println(aaaaa++ + " of " + pageSize + " done" + " ("+ page + "/"+ totalItemCount/pageSize +")");
			}
		}

		printProducts(products, ingredients, informations);
	}

	private static void printProducts(List<Product> products, Map<Product, List<Ingredient>> ingredients,
		Map<Product, Information> informations) {
		for (Product product : products) {
			System.out.println("product = " + product);
			System.out.println("ingredients = " + ingredients.get(product));
			System.out.println("information.effect = " + informations.get(product).getEffect());
		}
		System.out.println("products.size() = " + products.size());
	}

	private static List<Product> getProducts(int page, int pageSize) {
		try {
			Response response = sender.retrieveProduct(page, pageSize);
			JsonArrayResolver<Product> resolver = new JsonArrayResolver<>(response.getBody(), Product.class);
			return resolver.getList();
		} catch (Exception e) {
			System.err.printf("error in: getProducts(%d, %d), retry\n", page, pageSize);
			return getProducts(page, pageSize);
		}
	}

	private static List<Ingredient> getIngredientsFor(Product product) {
		try {
			Response response = sender.retrieveIngredient(product.getReportNumber());
			JsonArrayResolver<Ingredient> resolver = new JsonArrayResolver<>(response.getBody(), Ingredient.class);
			return resolver.getList();
		} catch (Exception e) {
			System.err.printf("error in: getIngredientsFor(%s), retry\n", product.getName());
			return getIngredientsFor(product);
		}
	}

	private static Information getInformationFor(Product product) {
		try {
			Response response = sender.retrieveInformation(product.getLedgerNumber());
			HtmlTableResolver htmlTableResolver = new HtmlTableResolver(response.getBody());
			Map<String, String> map = htmlTableResolver.resolve(".page-container");
			return new InformationMapAdapter(map);
		} catch (Exception e) {
			System.err.printf("error in: getInformationFor(%s), retry\n", product.getName());
			return getInformationFor(product);
		}
	}

}
