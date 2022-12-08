package example.sample.project.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import example.sample.project.domain.FoodItem;

// 인터페이스 Validator
@Component	// 알아서 객체를 만들어 줌
public class FoodItemValidator implements Validator {

	// 내가 검증을 하고자 하는 클래스 작성 (어댑터의 패턴)
	@Override
	public boolean supports(Class<?> clazz) {
		return FoodItem.class.isAssignableFrom(clazz);
	
	}
	
	//   
	@Override
	public void validate(Object target, Errors errors) {
		//					자식객체로 강제 형변환
		FoodItem foodItem = (FoodItem)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required.foodItem.itemName");
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
												// 객체명 		필드 			메세지
			errors.rejectValue("content", "required.foodItem.content", "default message()");
		}
		
		if(foodItem.getPrice() > 10000) {
			errors.rejectValue("price","max.foodItem.price", new Object[] {1, 10000, foodItem.getPrice()} , "default message()" );
		}
		
	
		if(foodItem.getSoldout()) {
			errors.reject("failureMsg", null);
		}
			
	}
	
}
