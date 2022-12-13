package example.sample.project.repository.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import example.sample.project.domain.FoodItem;
import example.sample.project.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j	// 혹시나 로그를 찍을 수도 있어서 만들어둠
@RequiredArgsConstructor
@Repository
public class MybatisFoodItemRepository implements FoodItemRepository{

	
	private final FoodItemMapper foodItemMapper;

	@Override
	public FoodItem insert(FoodItem foodItem) {
		Integer result = foodItemMapper.insert(foodItem);
		log.info("FoodItem insert result {}", result);
		return foodItem;
	}

	@Override
	public FoodItem selectById(int id) {
		FoodItem foodItem = foodItemMapper.selectById(id);
		return foodItem;
	}

	@Override
	public List<FoodItem> selectAll() {
		List<FoodItem> foodItems = foodItemMapper.selectAll();
		return foodItems;
	}

	@Override
	public void deleteAll() {
		foodItemMapper.deleteAll();
	}
	
	@Override
	public boolean update(int id, FoodItem foodItem) {
		boolean result = false;
		try {
			foodItemMapper.update(id, foodItem);
			result = true;
		} catch (Exception e) {
			log.error("foodItemMapper update error {} {}", id, foodItem);
		}
		return result;
	}
}
