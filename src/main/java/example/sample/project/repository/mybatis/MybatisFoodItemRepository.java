package example.sample.project.repository.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import example.sample.project.domain.FoodItem;
import example.sample.project.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MybatisFoodItemRepository implements FoodItemRepository{

	
	private final FoodItemMapper foodItemMapper;

	@Override
	public FoodItem insert(FoodItem foodItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodItem selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoodItem> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}
}
