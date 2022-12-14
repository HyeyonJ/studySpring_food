package example.sample.project.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import example.sample.project.domain.FoodItem;
import example.sample.project.domain.FoodItemCond;

@Mapper
public interface FoodItemMapper {

	public Integer insert(FoodItem foodItem);
	
	public Integer insertFoodItemOptions(@Param("id") int id, @Param("options") String options);
	
	public FoodItem selectById(int id);
	
	public List<FoodItem> selectAll();
	
	public List<FoodItem> selectSearchAll(FoodItemCond searchCond);
	
	public boolean update(@Param("id") int id, @Param("updateItem") FoodItem foodItem);
//	public void update(int id, FoodItem foodItem);
//	public integer update(int id, FoodItem foodItem);
	
	public void deleteAll();
	
}
