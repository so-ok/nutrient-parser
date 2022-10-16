package domain;

import java.util.Map;

public class InformationMapAdapter implements Information {

	private final Information information;

	public InformationMapAdapter(Map<String, String> informationMap) {
		this.information = InformationImpl.builder()
			.manufacturer(informationMap.get("업소명"))
			.expiry(informationMap.get("유통/소비기한"))
			.effect(informationMap.get("기능성 내용"))
			.reportNumber(Long.parseLong(informationMap.get("신고번호")))
			.shape(informationMap.get("성상"))
			.preservation(informationMap.get("보존 및 유통기준"))
			.registerDate(informationMap.get("등록일자"))
			.packagingMaterial(informationMap.get("포장재질"))
			.name(informationMap.get("제품명"))
			.warning(informationMap.get("섭취시주의사항"))
			.standard(informationMap.get("기준 및 규격"))
			.method(informationMap.get("섭취량/섭취 방법"))
			.packaging(informationMap.get("포장방법"))
			.build();
	}

	@Override
	public String getManufacturer() {
		return information.getManufacturer();
	}

	@Override
	public String getName() {
		return information.getName();
	}

	@Override
	public Long getReportNumber() {
		return information.getReportNumber();
	}

	@Override
	public String getRegisterDate() {
		return information.getRegisterDate();
	}

	@Override
	public String getExpiry() {
		return information.getExpiry();
	}

	@Override
	public String getShape() {
		return information.getShape();
	}

	@Override
	public String getMethod() {
		return information.getMethod();
	}

	@Override
	public String getPackaging() {
		return information.getPackaging();
	}

	@Override
	public String getPackagingMaterial() {
		return information.getPackagingMaterial();
	}

	@Override
	public String getPreservation() {
		return information.getPreservation();
	}

	@Override
	public String getWarning() {
		return information.getWarning();
	}

	@Override
	public String getEffect() {
		return information.getEffect();
	}

	@Override
	public String getStandard() {
		return information.getStandard();
	}
}
