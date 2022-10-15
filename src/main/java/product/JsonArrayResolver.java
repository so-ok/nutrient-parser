package product;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonArrayResolver {

	private final List<Product> objects;

	public JsonArrayResolver(String response) {
		this.objects = resolve(response);
	}

	private List<Product> resolve(String response) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readerForListOf(Product.class).readValue(response);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> getList() {
		return objects;
	}
}
