package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * {
 * "prdlst_report_ledg_no": "2022021000416566",
 * "prdlst_nm": "수면엔 테라슬립 락티움 제트 솔루션 ZZZ",
 * "prms_dt": "20221014",
 * "prdlst_report_no": "200400200082583",
 * "bssh_nm": "주식회사 노바렉스",
 * "total_count": "31874",
 * "no": "1"
 * }
 */

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

	private final Long ledgerNumber;
	private final Long reportNumber;
	private final String name;
	private final String registerDate;
	private final String manufacturer;

	@JsonCreator
	public Product(@JsonProperty("prdlst_report_ledg_no") Long ledgerNumber,
		@JsonProperty("prdlst_report_no") Long reportNumber, @JsonProperty("prdlst_nm") String name,
		@JsonProperty("prms_dt") String registerDate, @JsonProperty("bssh_nm") String manufacturer) {
		this.ledgerNumber = ledgerNumber;
		this.reportNumber = reportNumber;
		this.name = name;
		this.registerDate = registerDate;
		this.manufacturer = manufacturer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Product product = (Product)o;

		if (!ledgerNumber.equals(product.ledgerNumber))
			return false;
		if (!reportNumber.equals(product.reportNumber))
			return false;
		if (!name.equals(product.name))
			return false;
		if (!registerDate.equals(product.registerDate))
			return false;
		return manufacturer.equals(product.manufacturer);
	}

	@Override
	public int hashCode() {
		int result = ledgerNumber.hashCode();
		result = 31 * result + reportNumber.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + registerDate.hashCode();
		result = 31 * result + manufacturer.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Product{" +
			"ledgerNumber=" + ledgerNumber +
			", reportNumber=" + reportNumber +
			", name='" + name + '\'' +
			", registerDate='" + registerDate + '\'' +
			", manufacturer='" + manufacturer + '\'' +
			'}';
	}
}
