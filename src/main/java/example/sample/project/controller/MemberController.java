package example.sample.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import example.sample.project.domain.Member;
//import example.sample.project.repository.ListMemberRepository;
import example.sample.project.repository.mybatis.MybatisMemberRepository;
import example.sample.project.validation.MemberValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MybatisMemberRepository memberRepository;
//	private final ListMemberRepository memberRepository;
	private final MemberValidator memberValidator;
	
	@GetMapping("/new")
	public String newMember(Model model) {
		Member member = new Member();
		model.addAttribute(member);
		
		return "members/newMember";
	}
	
	@PostMapping("/new")
	public String newMemberInsert(@ModelAttribute Member member
			, BindingResult bindingResult) {
		log.info("member = {}", member);
		
		memberValidator.validate(member, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return "members/newMember";
		}
		
		memberRepository.insert(member);
		return "redirect:/";
	}
	
	@PostConstruct
	public void init() {
		Member member = new Member();
		member.setLoginId("admin");
		member.setPassword("admin");
		member.setName("관리자");
		
		Member member2 = new Member();
		member2.setLoginId("user1");
		member2.setPassword("user1");
		member2.setName("사용자1");
		
		Member member3 = new Member();
		member3.setLoginId("user2");
		member3.setPassword("user2");
		member3.setName("사용자2");
		
		memberRepository.insert(member);
		memberRepository.insert(member2);
		memberRepository.insert(member3);
	}
}
