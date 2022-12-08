package example.sample.project.controller;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import example.sample.project.validation.form.LoginForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		
		return "login/login";
	}
	
	@PostMapping("/login")
			// 화면에서 login id password 입력 받은 후 binding 처리
	public String doLogin(@ModelAttribute LoginForm loginForm,
			// Validator 상속받는 클래스에서 객체값을 (데이터) 검증
			BindingResult bindingResult) {
		log.info("loginForm {}", loginForm);
		
		validateLoginForm(loginForm, bindingResult);
		
		return "";
	}
	
	public void validateLoginForm(LoginForm loginForm, Errors errors) {
		if(!StringUtils.hasText(loginForm.getLoginId())) {
			errors.rejectValue("lodinId", null, "아이디 필수 입력입니다.");
		}
		
		if(!StringUtils.hasText(loginForm.getPassword())) {
			errors.rejectValue("password", null, "비밀번호 필수 입력입니다.");
		}
		
		
	}
}
