package resolver;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonArrayResolver<T> {

	private final List<T> objects;

	public JsonArrayResolver(String response, Class<T> targetClass) {
		this.objects = resolve(response, targetClass);
	}

	private List<T> resolve(String response, Class<T> targetClass) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readerForListOf(targetClass).readValue(response);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<T> getList() {
		return objects;
	}
}
