package example.sample.project.domain;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FoodItemCond {

	private Integer id;
	private String itemName;
	private String content;
	private int price;
	
	private Boolean soldout;
	private List<String> options;
	private FoodType foodType;
	private String shopCode;
}
