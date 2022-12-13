package example.sample.project.repository.mybatis;

import java.util.List;

import example.sample.project.domain.FoodItem;

public interface FoodItemMapper {

	public Integer insert(FoodItem foodItem);
	
	public FoodItem selectById(int id);
	
	public List<FoodItem> selectAll();
	
//	public boolean update(int id, FoodItem foodItem);
	public void update(int id, FoodItem foodItem);
//	public integer update(int id, FoodItem foodItem);
	
	public void deleteAll();
	
}