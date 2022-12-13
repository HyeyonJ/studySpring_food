package example.sample.project.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import example.sample.project.domain.FoodItem;
import example.sample.project.domain.FoodType;


@Repository
public class ListFoodRepository implements FoodItemRepository {
	private static List<FoodItem>db = new ArrayList<>();
//	private List<FoodItem>db;
	//key, value
	private static int seq = 1;
	
	// 싱글톤 : 객체의 인스턴스 오직 한개만 생성되는 패턴 
	// 해당 객체에 접근 시 메모리 낭비 방지
//	private static final FoodRepository instance = new FoodRepository();
//	
//	public static FoodRepository getInstance() {
//		return instance;
//	}
//	private FoodRepository() {
//		
//	}
	
	public FoodItem insert(FoodItem foodItem) {
		foodItem.setId(seq++);
		db.add(foodItem);
		return foodItem;
	}

	public FoodItem selectById (int id) {
		for(FoodItem foodItem : db) {
			if(foodItem.getId() == id) {
				return foodItem;
			}
		}
		return null;
	}
	
	public List<FoodItem> selectAll(){
		return db;
	}
	
	public boolean update(int id, FoodItem foodItem) {
		
		boolean result = false;
		
		try {
		FoodItem targetFoodItem = selectById(id);
		targetFoodItem.setItemName(foodItem.getItemName());
		targetFoodItem.setContent(foodItem.getContent());
		targetFoodItem.setPrice(foodItem.getPrice());
		targetFoodItem.setSoldout(foodItem.getSoldout());
		targetFoodItem.setOptions(foodItem.getOptions());
		targetFoodItem.setFoodType(foodItem.getFoodType());
		targetFoodItem.setShopCode(foodItem.getShopCode());
		
		
		// FoodItem에서 필드 추가
		// 화면에서 Input 처리
		// 전달받는 부분 @ModelAttribute -> 알아서 필드 맵핑
		// Controller -> Repository update
		result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
		
	}
	
	public void deleteAll() {
		db.clear();
	}
}
