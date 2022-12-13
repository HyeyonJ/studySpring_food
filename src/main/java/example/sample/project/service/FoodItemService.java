package example.sample.project.service;

import org.springframework.stereotype.Service;

import example.sample.project.domain.FoodItem;
import example.sample.project.domain.Member;
import example.sample.project.repository.FoodItemRepository;
import example.sample.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FoodItemService {

	
private final FoodItemRepository foodRepository;
	
	public FoodItem login(String itemName, String content, int price, int id) {
		FoodItem foodItem = foodRepository.selectById(id);
//		if(member == null ) {
//			return null;	// null이 기본값
//		} else {
//			if(member.getPassword().equals(password)) {
//				return member;
//			}
//		}
//		return null;
		
		// 위의 것을 간단하게 만든
//		if(foodItem != null ) {
//			if(foodItem.getId().equals(id)) {
//				return foodItem;
//			}
//		}
//		return null;	// 없으면 null을 리턴
		return foodItem;
	}
}
