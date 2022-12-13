package example.sample.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import example.sample.project.repository.ListMemberRepository;
import example.sample.project.repository.MemberRepository;
import example.sample.project.repository.mybatis.MemberMapper;
import example.sample.project.repository.mybatis.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MemberConfig {

	private final MemberMapper memberMapper;
	
	@Bean
	public MemberRepository memberRepository() {
		return new MybatisMemberRepository(memberMapper);
//		return new ListMemberRepository();
	}
}
