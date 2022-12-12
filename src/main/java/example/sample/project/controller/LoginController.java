


package example.sample.project.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import example.sample.project.domain.Member;
import example.sample.project.service.LoginService;
import example.sample.project.session.SessionManager;
import example.sample.project.session.SessionVar;
import example.sample.project.validation.form.LoginForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final SessionManager sessionManager;

	@GetMapping("/login")
	public String login(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);

		return "login/login";
	}

	@PostMapping("/login_cookie")
	public String doLogin_cookie(@ModelAttribute LoginForm loginForm, 
									BindingResult bindingResult,
//			 						HttpSession session,
									HttpServletResponse resp) {

		log.info("loginForm {}", loginForm);

		validateLoginForm(loginForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "login/login";
		}

		Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

		log.info("login {}", member);

		if (member == null) {
// 계정정보가 없거나 비밀번호가 안맞거나 로그인 실패
			bindingResult.reject("loginForm", "아이디 or 비밀번호");
			return "login/login";
		}


// 쿠키를 추가
		Cookie cookie = new Cookie("loginId", member.getLoginId());
		Cookie cookie2 = new Cookie("memberId", member.getId().toString());
		resp.addCookie(cookie);
		resp.addCookie(cookie2);

// session.setAttribute("name", member.getName());

		return "redirect:/";
	}
	
	
	@PostMapping("/login")
	public String doLogin(@ModelAttribute LoginForm loginForm, 
			BindingResult bindingResult, HttpServletResponse resp,
			HttpServletRequest req,
			@RequestParam(name="redirectURL", defaultValue="/") String redirectURL) {

		log.info("loginForm {}", loginForm);

		validateLoginForm(loginForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "login/login";
		}

		Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

		log.info("login {}", member);

		if (member == null) {
// 계정정보가 없거나 비밀번호가 안맞거나 로그인 실패
			bindingResult.reject("loginForm", "아이디 or 비밀번호");
			return "login/login";
		}

// 정상적으로 로그인 처리가 된 경우

// 세션을 추가
// true(default) => 없으면 새로 만듦
// false => 없어도 새로 안만듦
		// 정상적으로 로그인 처리가 된 경우
				// 세션에 추가
		HttpSession session = req.getSession(false);
		session.setAttribute(SessionVar.LOGIN_MEMBER, member);
		session.setMaxInactiveInterval(540);
		return "redirect:" + redirectURL;
//		HttpSession session = req.getSession();
//		session.setAttribute(SessionVar.LOGIN_MEMBER, member);
//		session.setMaxInactiveInterval(540);
//		return "redirect:/";
	}

	@PostMapping("/login_old")
	public String doLogin_old(@ModelAttribute LoginForm loginForm,
	BindingResult bindingResult, HttpServletResponse resp,
	HttpServletRequest req) {

	log.info("loginForm {}", loginForm);

	validateLoginForm(loginForm, bindingResult);

	if (bindingResult.hasErrors()) {
	return "login/login";
	}

	Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

	log.info("login {}", member);

	if (member == null) {
	// 계정정보가 없거나 비밀번호가 안맞거나 로그인 실패
	bindingResult.reject("loginForm", "아이디 or 비밀번호");
	return "login/login";
	}

	// 정상적으로 로그인 처리가 된 경우

	// 세션을 추가
	// true(default) => 없으면 새로 만듦
	// false => 없어도 새로 안만듦
	HttpSession session = req.getSession();
	session.setAttribute(SessionVar.LOGIN_MEMBER, member);
	session.setMaxInactiveInterval(540);
	return "redirect:/";
	}
	
	@PostMapping("/login_session")
	public String doLogin_session(@ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpSession session,
			HttpServletResponse resp) {

		log.info("loginForm {}", loginForm);

		validateLoginForm(loginForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "login/login";
		}

		Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

		log.info("login {}", member);

		if (member == null) {
// 계정정보가 없거나 비밀번호가 안맞거나 로그인 실패
			bindingResult.reject("loginForm", "아이디 or 비밀번호");
			return "login/login";
		}

// 정상적으로 로그인 처리가 된 경우

// 세션을 추가
		sessionManager.create(member, resp);
// 1) sessionMap 세션정보가 추가
// 2) resp -> sessionId Cookie값을 추가

		session.setAttribute("name", member.getName());

		return "redirect:/";
	}

	public void validateLoginForm(LoginForm loginForm, Errors errors) {

		if (!StringUtils.hasText(loginForm.getLoginId())) {
			errors.rejectValue("loginId", null, "아이디 필수 입력입니다.");
		}

		if (!StringUtils.hasText(loginForm.getPassword())) {
			errors.rejectValue("password", null, "비밀번호 필수 입력입니다.");
		}
	}

	@PostMapping("/logout_cookie")
	public String logout_cookie(HttpServletResponse resp) {
		Cookie cookie = new Cookie("memberId", null);
// 쿠키 죽이기
		cookie.setMaxAge(0);
		resp.addCookie(cookie);

		return "redirect:/";
	}

	@PostMapping("/logout_session")
	public String logout_session(HttpServletRequest req) {
		sessionManager.remove(req);

		return "redirect:/";
	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		return "redirect:/";
	}
}

//package example.sample.project.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import example.sample.project.domain.Member;
//import example.sample.project.service.LoginService;
//import example.sample.project.session.SessionManager;
//import example.sample.project.session.SessionVar;
//import example.sample.project.validation.form.LoginForm;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class LoginController {
//	
//	private final LoginService loginService;
//	private final SessionManager sessionManager;
//
//	@GetMapping("/login")
//	public String login(Model model) {
//		LoginForm loginForm = new LoginForm();
//		model.addAttribute("loginForm", loginForm);
//		
//		return "login/login";
//	}
//	
//	@PostMapping("/login")
//	// 화면에서 login id password 입력 받은 후 binding 처리
//	public String doLogin(@ModelAttribute LoginForm loginForm,
//			// Validator 상속받는 클래스에서 객체값을 (데이터) 검증
//			BindingResult bindingResult, HttpServletResponse resp
//			, HttpServletRequest req) {
//		log.info("loginForm {}", loginForm);
//
//		validateLoginForm(loginForm, bindingResult);
//// 에러가 있는지 에러가 있으면 넘기면 안됨
//		if (bindingResult.hasErrors()) {
//			return "login/login";
//		}
//// 받아서 확인
//		Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//
//		log.info("login {}", member);
//
//		if (member == null) { // 계정 정보가 없거나, 비밀번호가 안맞거나 로그인 실패
//			bindingResult.reject("loginForm", "아이디 혹은 비밀번호 불일치");
//			return "login/login";
//		}
//// 정상적으로 로그인 처리가 된 경우
//
//// 세션에 추가
////		sessionManager.create(member, resp);
//		HttpSession session = req.getSession();
//		session.setMaxInactiveInterval(540);
//		session.setAttribute(SessionVar.LOGIN_MEMBER, session);
////		req.getSession(false);	// boolean타입으로 뭔가 가능함
//		// ->1)  sessionMap 세션정보가 추가
//		// 2) resp -> sessionId cookie 값을 추가
//		
//
//		return "redirect:/";
//	}
//	
//	@PostMapping("/login_cookie")
//			// 화면에서 login id password 입력 받은 후 binding 처리
//	public String doLogin_cookie(@ModelAttribute LoginForm loginForm,
//			// Validator 상속받는 클래스에서 객체값을 (데이터) 검증
//			BindingResult bindingResult,
//			HttpServletResponse resp
//			) {
//		log.info("loginForm {}", loginForm);
//		
//		validateLoginForm(loginForm, bindingResult);
//		// 에러가 있는지 에러가 있으면 넘기면 안됨
//		if(bindingResult.hasErrors()) {
//			return "login/login";
//		}
//		// 받아서 확인
//		Member member= loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//		
//		log.info("login {}", member);
//		
//		if(member == null) {	//계정 정보가 없거나, 비밀번호가 안맞거나 로그인 실패
//			bindingResult.reject("loginForm", "아이디 혹은 비밀번호 불일치");
//			return "login/login";
//		}
//		// 정상적으로 로그인 처리가 된 경우
//		
//		// 쿠키를 추가
//		Cookie cookie = new Cookie("loginId", member.getLoginId());
//		Cookie cookie2 = new Cookie("memberId", member.getId().toString());
//		resp.addCookie(cookie2);
//		
//		return "redirect:/";
//	}
//	
//	public void validateLoginForm(LoginForm loginForm, Errors errors) {
//		if(!StringUtils.hasText(loginForm.getLoginId())) {
//			errors.rejectValue("loginId", null, "아이디 필수 입력입니다.");
//		}
//		
//		if(!StringUtils.hasText(loginForm.getPassword())) {
//			errors.rejectValue("password", null, "비밀번호 필수 입력입니다.");
//		}
//		
//	}
//	@PostMapping("/logout")
//	public String logout(HttpServletRequest req, HttpServletResponse resp) {
//		HttpSession session = req.getSession(false);
//
//		if(session != null) {
//			session.invalidate();
//		}
//		return "redirect:/";
//	}
//	
//	@PostMapping("/logout_Session")
//	public String logout_Session(HttpServletResponse resp, HttpServletRequest req) {
//		// 세션 사용자쪽에 tempSessionId Cookie.
//		// 세션 주제 서버
//		// 서버 세션메니저 
//		sessionManager.remove(req);
//		// 
//		return "redirect:/";
//	}
//	
//	@PostMapping("/logout_cookie")
//	public String logout_cookie(HttpServletResponse resp) {
//		// 쿠키 없애기
//		Cookie cookie = new Cookie("memberId", null);
//		cookie.setMaxAge(0);
//		resp.addCookie(cookie);
//		return "redirect:/";
//	}
//}
