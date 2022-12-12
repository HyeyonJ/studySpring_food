package example.sample.project.repository;

import java.util.List;

import example.sample.project.domain.Member;

public interface MemberRepository {

	
	public Member insert(Member member);

	public Member selectById (int id);
	
	public Member selectByLoginId (String loginId);
	
	public List<Member> selectAll();
	
	public void deleteAll();
}
