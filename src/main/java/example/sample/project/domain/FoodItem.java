package example.sample.project.domain;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class FoodItem {
	private int id;
	
//	@NotBlank(message="필수입력좀..")
	@NotBlank
	private String itemName;
	
	@NotBlank
	private String content;
	
	@Range(min=1, max=10000)
	private int price;
	
	private Boolean soldout;
	private List<String> options;
	private FoodType foodType;
	private String shopCode;
	
	public FoodItem() {
		// TODO Auto-generated constructor stub
	}
	
	public FoodItem(String itemName, String content, int price) {
		super();
		this.itemName = itemName;
		this.content = content;
		this.price = price;
	}

}


