package example.sample.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import example.sample.project.repository.FoodItemRepository;
import example.sample.project.repository.FoodRepository;
import example.sample.project.repository.MemberRepository;
import example.sample.project.repository.mybatis.FoodItemMapper;
import example.sample.project.repository.mybatis.MemberMapper;
import example.sample.project.repository.mybatis.MybatisFoodItemRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FoodItemConfig {

	private final FoodItemMapper foodItemMapper;

	@Bean
	public FoodItemRepository foodRepository() {
		return new MybatisFoodItemRepository(foodItemMapper);
//		return new ListMemberRepository();
	}
}
