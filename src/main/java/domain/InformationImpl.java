package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class InformationImpl implements Information {

	private final String name;
	private final String manufacturer;
	private final String expiry;
	private final String effect;
	private final Long reportNumber;
	private final String shape;
	private final String preservation;
	private final String registerDate;
	private final String method;
	private final String packagingMaterial;
	private final String packaging;
	private final String warning;
	private final String standard;
}
