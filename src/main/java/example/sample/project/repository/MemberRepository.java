package example.sample.project.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import example.sample.project.domain.Member;

@Repository
public class MemberRepository {
	private static List<Member>db = new ArrayList<>();
	//key, value
	private static int seq = 1;
	
	// 싱글톤 : 객체의 인스턴스 오직 한개만 생성되는 패턴 
	// 해당 객체에 접근 시 메모리 낭비 방지
//	private static final FoodRepository instance = new FoodRepository();
//	
//	public static FoodRepository getInstance() {
//		return instance;
//	}
//	private FoodRepository() {
//		
//	}
	
	public Member insert(Member member) {
		member.setId(seq++);
		db.add(member);
		return member;
	}

	public Member selectById (int id) {
		for(Member member : db) {
			if(member.getId() == id) {
				return member;
			}
		}
		return null;
	}
	
	public Member selectByLoginId (String loginId) {
		for(Member member : db) {
			if(member.getLoginId().equals(loginId)) {
				return member;	// 아이디 값 있으면 member리턴
			}
		}
		return null;	// 없으면
	}
	
	public List<Member> selectAll(){
		return db;
	}
	
	
	public void deleteAll(){
		db.clear();
	}
}
