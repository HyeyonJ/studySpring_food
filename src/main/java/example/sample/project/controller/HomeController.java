package example.sample.project.controller;

import java.util.Enumeration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import example.sample.project.domain.Member;
import example.sample.project.repository.MemberRepository;
import example.sample.project.session.SessionManager;
import example.sample.project.session.SessionVar;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

	private final MemberRepository memberRepository;
	private final SessionManager sessionManager;

	@GetMapping("/")
	public String home(Model model, HttpServletRequest req) {

		HttpSession session = req.getSession();
		if (session == null) {
			return "home";
		}

		// session 존재
//		for(String sName : session.getAttributeNames()model..asIterator())
		Enumeration<String> sessionName = session.getAttributeNames();
		while(sessionName.hasMoreElements()) {
			String name = sessionName.nextElement();
			log.info("session {}, {}", name, session.getAttribute(name));
		}
		
		log.info("{},{},{},{},{}"
					, session.getId()
					, session.getMaxInactiveInterval()
					, session.getCreationTime()
					, session.getLastAccessedTime()
					, session.isNew());
		
		
		Member member = (Member) session.getAttribute(SessionVar.LOGIN_MEMBER);

		if (member == null) {
			return "home";
		}
		model.addAttribute("member", member);
		return "loginHome";
	}

	// @GetMapping("/")
	public String home_session(Model model, HttpServletRequest req) {

		Member member = (Member) sessionManager.getSessionObject(req);
		if (member == null) {
			return "home";
		}
		model.addAttribute("member", member);
		return "loginHome";
	}

	// @GetMapping("/")
	public String home_cookie(Model model,
			@CookieValue(name = "memberId", required = false) Integer memberId,
			@CookieValue(name = "loginId", required = false) String loginId) {

		log.info("memberId={}, loginId={}", memberId, loginId);

		if (memberId == null) {
			return "home";
		}

		Member member = memberRepository.selectById(memberId);

		if (member == null) {
			return "home";
		}

		model.addAttribute("member", member);

		return "loginHome";

	}

}


//package example.sample.project.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CookieValue;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import example.sample.project.domain.Member;
//import example.sample.project.repository.MemberRepository;
//import example.sample.project.session.SessionManager;
//import example.sample.project.session.SessionVar;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class HomeController {
//	
//	private final MemberRepository memberRespository;
//	private final SessionManager sessionManager;
//
//	@GetMapping("/")
//	public String home(Model model
//			, HttpServletRequest req ) {
//		
//		HttpSession session = req.getSession(false);
//		if(session == null) {
//			return "home";
//		}
//		
//		Member member = (Member)session.getAttribute(SessionVar.LOGIN_MEMBER);
//		
//		if(member == null) {
//			return "home";
//		}
//		model.addAttribute("member", member);
//		return "loginHome";
//	}
//	
//	@GetMapping("/")
//	public String home_Session(Model model
//			, HttpServletRequest req ) {
//		
//		Member member = (Member)sessionManager.getSessionObject(req);
//		if(member == null) {
//			return "home";
//		}
//		model.addAttribute("member", member);
//		return "loginHome";
//	}
//	
////	@GetMapping("/")
//	public String home_cookie(Model model
//			, @CookieValue(name="memberId", required=false) Integer memberId
//			, @CookieValue(name="loginId", required=false) String loginId) {
//		
//		log.info("memberId={}, loginId= {}", memberId, loginId);
//		
//		if(memberId == null) {
//			return "home";
//		}
//		
//		Member member = memberRespository.selectById(memberId);
//		if(member == null){
//			return "home";
//		}
//		model.addAttribute("member", member);
//		return "loginHome";
//	}
//}
