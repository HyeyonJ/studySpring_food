package example.sample.project.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import example.sample.project.domain.Member;

@Mapper
public interface MemberMapper {
//	public Member insert(Member member);
	public Integer insert(Member member);
	
	public Member selectById(int id);
	
	public Member selectByLoginId(String loginId);
	
	public List<Member> selectAll();
	
	public void deleteAll();
}
