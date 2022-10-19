import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.Response;

import domain.Information;
import domain.InformationMapAdapter;
import domain.Ingredient;
import domain.Product;
import resolver.HtmlTableResolver;
import resolver.JsonArrayResolver;

public class Controller {

	private static final Sender sender = new Sender();
	private static final ExecutorService executorService = Executors.newFixedThreadPool(24);

	public static void main(String[] args) throws InterruptedException {
		final List<Product> products = new ArrayList<>();
		final Map<Product, List<Ingredient>> ingredients = new HashMap<>();
		final Map<Product, Information> informations = new HashMap<>();

		final int pageSize = 300;
		final int totalItemCount = 300;

		for (int i = 1, page = 1; i <= totalItemCount; i += pageSize, page++) {
			List<Product> resolvedProducts = getProducts(page, pageSize);
			products.addAll(resolvedProducts);

			resolveAdditionalFor(resolvedProducts, ingredients, informations);
			System.out.printf(" (%d/%d)\n", page, ceilDivide(totalItemCount, pageSize));
		}

		executorService.shutdown();
		if (executorService.awaitTermination(60, TimeUnit.MINUTES)) {
			printProducts(products, ingredients, informations);
			return;
		}
		System.err.println("크롤링에 문제가 발생했습니다");
	}

	private static void resolveAdditionalFor(final List<Product> resolvedProducts,
		final Map<Product, List<Ingredient>> ingredients, final Map<Product, Information> informations) {
		for (final Product product : resolvedProducts) {
			Runnable ingredientFetcher = () -> {
				List<Ingredient> resolvedIngredients = getIngredientsFor(product);
				ingredients.put(product, resolvedIngredients);
			};
			Runnable informationFetcher = () -> {
				Information information = getInformationFor(product);
				informations.put(product, information);
			};

			executorService.execute(informationFetcher);
			executorService.execute(ingredientFetcher);
			System.out.printf("%d requested\n", product.getReportNumber());
		}
	}

	private static List<Product> getProducts(final int page, final int pageSize) {
		try {
			Response response = sender.retrieveProduct(page, pageSize);
			JsonArrayResolver<Product> resolver = new JsonArrayResolver<>(response.getBody(), Product.class);
			return resolver.getList();
		} catch (Exception e) {
			System.err.printf("error in: getProducts(%d, %d), retry\n", page, pageSize);
			return getProducts(page, pageSize);
		}
	}

	private static List<Ingredient> getIngredientsFor(final Product product) {
		try {
			Response response = sender.retrieveIngredient(product.getReportNumber());
			JsonArrayResolver<Ingredient> resolver = new JsonArrayResolver<>(response.getBody(), Ingredient.class);
			return resolver.getList();
		} catch (Exception e) {
			System.err.printf("error in: getIngredientsFor(%s), retry\n", product.getName());
			List<Ingredient> result = getIngredientsFor(product);
			System.err.printf("getIngredientsFor(%s) retry success\n", product.getName());
			return result;
		}
	}

	private static Information getInformationFor(final Product product) {
		try {
			Response response = sender.retrieveInformation(product.getLedgerNumber());
			HtmlTableResolver htmlTableResolver = new HtmlTableResolver(response.getBody());
			Map<String, String> map = htmlTableResolver.resolve(".page-container");
			return new InformationMapAdapter(map);
		} catch (Exception e) {
			System.err.printf("error in: getInformationFor(%s), retry\n", product.getName());
			Information result = getInformationFor(product);
			System.err.printf("getInformationFor(%s) retry success\n", product.getName());
			return result;
		}
	}

	private static void printProducts(final List<Product> products, final Map<Product, List<Ingredient>> ingredients,
		final Map<Product, Information> informations) {
		for (final Product product : products) {
			System.out.println("product = " + product);
			System.out.println("ingredients = " + ingredients.get(product));
			System.out.println("information.effect = " + informations.get(product).getEffect());
		}
		System.out.println("products.size() = " + products.size());
	}

	private static int ceilDivide(int number, int divideNumber) {
		if (number % divideNumber > 0) {
			return number / divideNumber + 1;
		}
		return number / divideNumber;
	}
}
