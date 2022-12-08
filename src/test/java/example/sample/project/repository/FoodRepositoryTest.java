package example.sample.project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import example.sample.project.domain.FoodItem;

public class FoodRepositoryTest {
	FoodRepository foodRepository ;
//	= FoodRepository.getInstance();
	
	@Test
	void updateTest() {
		// 부르기
		FoodItem foodItem = new FoodItem("햄버거" , "싸이버거", 6000);
		// 값 저장 해서 리턴 올 것들을 변수에 담음
		FoodItem foodItemInserted = foodRepository.insert(foodItem);
		
		// 1, "햄버거", "싸이버거", 7000 번호는 별도로 관리 하기 때문에 그냥 두고, 가격만 바꿈
		FoodItem updatefoodItem = new FoodItem("햄버거" , "싸이버거", 7000);
		boolean result = foodRepository.update(foodItemInserted.getId(), updatefoodItem);
	
		if(result) {
			// 정상 업데이트 된 경우
		} else {
			// 업데이트 실패한 경우
		}
		// id : 1 햄버거를 6천원
		// id : 1 햄버거를 7천원 (updateFoodItem)
		
		// 비교를 하려고 하는 타겟
		FoodItem targetFoodItem = foodRepository.selectById(foodItemInserted.getId());
	
		assertThat(targetFoodItem.getPrice()).isEqualTo(updatefoodItem.getPrice());
		assertThat(targetFoodItem.getItemName()).isEqualTo(updatefoodItem.getItemName());
		assertThat(targetFoodItem.getContent()).isEqualTo(updatefoodItem.getContent());
	}
}
