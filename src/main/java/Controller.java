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
	private static final List<Thread> threads = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {
		List<Product> products = new ArrayList<>();
		Map<Product, List<Ingredient>> ingredients = new HashMap<>();
		Map<Product, Information> informations = new HashMap<>();

		int pageSize = 100;
		int totalItemCount = 1000;
		for (int i = 1, page = 1; i <= totalItemCount; i += pageSize, page++) {
			List<Product> resolvedProducts = getProducts(page, pageSize);
			products.addAll(resolvedProducts);

			for (Product product : resolvedProducts) {
				Runnable ingredientFetcher = () -> {
					List<Ingredient> resolvedIngredients = getIngredientsFor(product);
					ingredients.put(product, resolvedIngredients);
				};
				Runnable informationFetcher = () -> {
					Information information = getInformationFor(product);
					informations.put(product, information);
				};

				runAsThread(ingredientFetcher);
				runAsThread(informationFetcher);
				System.out.printf("%d requested (%d/%d)\n", product.getReportNumber(), page, totalItemCount / pageSize);
			}
			joinThreads();
			threads.clear();
		}

		joinThreads();
		printProducts(products, ingredients, informations);
	}

	private static void joinThreads() throws InterruptedException {
		for (Thread thread : threads) {
			thread.join();
		}
	}

	private static void runAsThread(Runnable ingredientRunner) {
		Thread thread = new Thread(ingredientRunner);
		threads.add(thread);
		thread.start();
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
			List<Ingredient> result = getIngredientsFor(product);
			System.err.printf("getIngredientsFor(%s) retry success\n", product.getName());
			return result;
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
			Information result = getInformationFor(product);
			System.err.printf("getInformationFor(%s) retry success\n", product.getName());
			return result;
		}
	}

}
