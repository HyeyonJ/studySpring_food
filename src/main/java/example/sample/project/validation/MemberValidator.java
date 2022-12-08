package example.sample.project.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import example.sample.project.domain.Member;

@Component
public class MemberValidator implements Validator {

	// 내가 검증을 하고자 하는 클래스 작성 (어댑터의 패턴)
	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.isAssignableFrom(clazz);
	
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		//					자식객체로 강제 형변환
		Member member = (Member)target;
		
		if(!StringUtils.hasText(member.getLoginId())){
			errors.rejectValue("loginId", null, "아이디는 필수 입력입니다");
		}
		
		if(!StringUtils.hasText(member.getPassword())){
			errors.rejectValue("password", null, "비밀번호는 필수 입력입니다");
		}
		
		if(!StringUtils.hasText(member.getName())){
			errors.rejectValue("name", null, "이름은 필수 입력입니다");
			
		}
		
	}
}
