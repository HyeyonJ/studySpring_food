package example.sample.project.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import example.sample.project.domain.FoodItem;
import example.sample.project.domain.FoodItemCond;
import example.sample.project.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j	// 혹시나 로그를 찍을 수도 있어서 만들어둠
@RequiredArgsConstructor
@Repository
public class MybatisFoodItemRepository implements FoodItemRepository{

	
	private final FoodItemMapper foodItemMapper;

//	@Override
//	public FoodItem insert(FoodItem foodItem) {
//		Integer result = foodItemMapper.insert(foodItem);
//		log.info("FoodItem insert result {}", result);
//		
//		for(String options : foodItem.getOptions()) {
//			foodItemMapper.insertFoodItemOptions(foodItem.getId(), options);
//		}
//		
//		return foodItem;
//	}

	@Override
	public FoodItem selectById(int id) {
//		FoodItem foodItem = foodItemMapper.selectById(id);
		FoodItem foodItem = foodItemMapper.selectByIdWithOptions(id);
		log.info("fooditem {}", foodItem);
		
//		List<String> options = foodItemMapper.selectFoodItemOptions(id);
//		foodItem.setOptions(options);
		return foodItem;
	}

	@Override
	public List<FoodItem> selectAll() {
		List<FoodItem> foodItems = null;
		try {
			foodItems = foodItemMapper.selectAll();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
			return foodItems;
	}
	
	@Override
	public void deleteAll() {
		foodItemMapper.deleteAll();
	}
	
	@Override
	@Transactional
	public boolean update(int id, FoodItem foodItem) {
		boolean result = false;
		try {
			foodItemMapper.update(id, foodItem);
			result = true;
			
			// 해당 id options 한번 삭제
			foodItemMapper.deleteFoodItemOptions(id);
			// id options 다시 insert (저장하는 것)
			for(String options : foodItem.getOptions()) {
				foodItemMapper.insertFoodItemOptions(foodItem.getId(), options);
			}
			
		} catch (Exception e) {
			log.error("foodItemMapper update error {} {}", id, foodItem);
		}
		return result;
	}
	
	@Override
	@Transactional
	public FoodItem insert(FoodItem foodItem) {
		Integer result = foodItemMapper.insert(foodItem);
		log.info("FoodItem insert result {}", result);
		
		for(String options : foodItem.getOptions()) {
			foodItemMapper.insertFoodItemOptions(foodItem.getId(), options);
		}
		
		return foodItem;
	}

	@Override
	public List<FoodItem> selectSearchAll(FoodItemCond searchCond) {
		List<FoodItem> foods = foodItemMapper.selectSearchAll(searchCond);
		return foods;
	}
}
