package example.sample.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import example.sample.project.repository.FoodItemRepository;
import example.sample.project.repository.ListMemberRepository;
import example.sample.project.repository.MemberRepository;
import example.sample.project.repository.mybatis.FoodItemMapper;
import example.sample.project.repository.mybatis.MemberMapper;
import example.sample.project.repository.mybatis.MybatisFoodItemRepository;
import example.sample.project.repository.mybatis.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppBeanConfig {

	private final MemberMapper memberMapper;
	private final FoodItemMapper foodItemMapper;
	
	@Bean
	public MemberRepository memberRepository() {
		return new MybatisMemberRepository(memberMapper);
//		return new ListMemberRepository();
	}
	
	@Bean
	public FoodItemRepository foodItemRepository() {
		return new MybatisFoodItemRepository(foodItemMapper);
	}
}
