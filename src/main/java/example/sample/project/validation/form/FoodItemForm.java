package example.sample.project.validation.form;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import example.sample.project.domain.FoodType;
import jakarta.validation.constraints.NotBlank;

public class FoodItemForm {

	private int id;
	
//	@NotBlank(message="필수입력좀..")
	@NotBlank(groups = UpdateCheck.class)
	private String itemName;
	
	@NotBlank(groups = NewCheck.class)
	private String content;
	
	@Range(min=1, max=10000, groups = {UpdateCheck.class, NewCheck.class})
	private int price;
	
	public FoodItemForm() {}

	private Boolean soldout;
	private List<String> options;
	private FoodType foodType;
	private String shopCode;
	
	public FoodItemForm(String itemName, String content, int price) {
		super();
		this.itemName = itemName;
		this.content = content;
		this.price = price;
	}
	
}
