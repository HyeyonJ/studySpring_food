package example.sample.project.repository.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import example.sample.project.domain.Member;
import example.sample.project.repository.MemberRepository;
import example.sample.project.validation.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MybatisMemberRepository implements MemberRepository{

	// db 쿼리를 수행하기 위해서 Mapper를 호출할 수 있는 mapper를 가지고 옴
	private final MemberMapper memberMapper;// DB처리는 이친구가 해줌
	
//	private final MemberValidator memberValidator;
	
	@Override
	public Member insert(Member member) {
		memberMapper.insert(member);
		return member;
	}

	@Override
	public Member selectById(int id) {
		// member가 넘어올 것이라고 기대
		Member member = memberMapper.selectById(id);
		// member를 넘겨줌
		return member;
	}

	@Override
	public Member selectByLoginId(String loginId) {
		Member member = memberMapper.selectByLoginId(loginId);	//맵핑
		return member;
	}

	@Override
	public List<Member> selectAll() {
		List<Member> members = memberMapper.selectAll();
		return members;
	}

	@Override
	public void deleteAll() {
		memberMapper.deleteAll();
	}

}
