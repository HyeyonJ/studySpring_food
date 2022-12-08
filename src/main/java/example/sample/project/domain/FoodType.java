package example.sample.project.domain;

public enum FoodType {
	KFOOD("한식"), JFOOD("일식"), CFOOD("중식");
		
	private final String memo;
	
	FoodType(String memo) {
		this.memo = memo;
	}
	
	public String getMemo() {
		return memo;
	}
}
