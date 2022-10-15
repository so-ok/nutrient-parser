package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * {
 * "rawmtrl_seq": "3",
 * "rawmtrl_nm": "유단백가수분해물",
 * "rawmtrl_dvs_cd": "011"
 * }
 */

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient {

	public enum Category {
		@JsonProperty("011")
		FUNCTIONAL("011"),
		@JsonProperty("021")
		UNKNOWN("021"),
		@JsonProperty("023")
		UNKNOWN_2("023"),
		@JsonProperty("031")
		OTHERS("031");
		@JsonEnumDefaultValue
		private final String number;

		Category(String number) {
			this.number = number;
		}

		public String getNumber() {
			return number;
		}
	}

	private final String name;
	private final Category category;

	@JsonCreator
	public Ingredient(@JsonProperty("rawmtrl_nm") String name, @JsonProperty("rawmtrl_dvs_cd") Category category) {
		this.name = name;
		this.category = category;
	}

	public boolean hasCategory(Category category) {
		return this.category == category;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Ingredient that = (Ingredient)o;

		if (!name.equals(that.name))
			return false;
		return category == that.category;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + category.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Ingredient{" +
			"name='" + name + '\'' +
			", category=" + category +
			'}';
	}
}
