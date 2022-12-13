package example.sample.project.repository;

import java.util.List;

import example.sample.project.domain.FoodItem;

public interface FoodItemRepository {

	public FoodItem insert(FoodItem foodItem);

	public FoodItem selectById (int id);
	
	public List<FoodItem> selectAll();
	
	public boolean update(int id, FoodItem foodItem);
	
	public void deleteAll();
}
