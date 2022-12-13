package example.sample.project.service;

import org.springframework.stereotype.Service;

import example.sample.project.domain.Member;
import example.sample.project.repository.ListMemberRepository;
import example.sample.project.repository.MemberRepository;
import example.sample.project.repository.mybatis.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {
	
//	private final ListMemberRepository memberRepository;
//	private final MybatisMemberRepository memberRepository;
	private final MemberRepository memberRepository;
	
	public Member login(String loginId, String password) {
		Member member = memberRepository.selectByLoginId(loginId);
//		if(member == null ) {
//			return null;	// null이 기본값
//		} else {
//			if(member.getPassword().equals(password)) {
//				return member;
//			}
//		}
//		return null;
		
		// 위의 것을 간단하게 만든
		if(member != null ) {
			if(member.getPassword().equals(password)) {
				return member;
			}
		}
		return null;	// 없으면 null을 리턴
	}
}
