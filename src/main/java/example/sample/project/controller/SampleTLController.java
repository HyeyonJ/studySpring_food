package example.sample.project.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import example.sample.project.domain.FoodItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SampleTLController {	// Thymeleaf
	
	@RequestMapping("/basic")
	public String text(Model model) {
						// 키 값      벨류 값
		model.addAttribute("test", "Test String");
		model.addAttribute("boldtest", "<b>Test String</b>");
		
		FoodItem foodItem1 = new FoodItem("name1", "content1", 1000);
		FoodItem foodItem2 = new FoodItem("name2", "content2", 2000);
		
		model.addAttribute("foodItem1", foodItem1);
		model.addAttribute("foodItem2", foodItem2);
		
		List<FoodItem> foods = new ArrayList<FoodItem>();
		foods.add(foodItem1);
		foods.add(foodItem2);
		
		//				   객체를 불러서 넘김
		model.addAttribute("foods", foods);
		
		return "sampleTL/basic";
	}
	
	@RequestMapping("/object")
	public String object(Model model) {
		model.addAttribute("dateTime", LocalDateTime.now());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String strDateTime = LocalDateTime.now().format(formatter);
		
		model.addAttribute("strDateTime", strDateTime);
		
		log.info(LocalDateTime.now().toString());
		
		return "sampleTL/object";
	}
	
	@GetMapping("/object/{param1}")
	public String updateFoodProcess(Model model
				, @PathVariable("param1") String param1
				, @PathVariable("param2") String param2) {
		log.info(param1);
		log.info(param2);
		return "foods/object";
	}

	@Component("helloBean")
	static class HelloBean{
		public String hello(String data) {
			return data;
		}
	}
	
	@GetMapping("/link")
	public String link(Model model) {
		//					이름 			벨류
		model.addAttribute("param1", "param1");
		model.addAttribute("param2", "param2");
		return "sampleTL/link";
	}
	
	@GetMapping("/literal")
	public String literal(Model model) {
		
		FoodItem foodItem1 = new FoodItem("name1", "content1", 1000);
		FoodItem foodItem2 = new FoodItem("name2", "content2", 2000);
		
		model.addAttribute("foodItem1", foodItem1);
		model.addAttribute("foodItem2", foodItem2);
		model.addAttribute("nullData", null);
		
		return "sampleTL/literal";
	}
	
	@GetMapping("/comments")
	public String comments(Model model) {
		model.addAttribute("text", "text");
		return "sampleTL/comments";
	}
	
	@GetMapping("/javascript")
	public String javascript(Model model) {
		model.addAttribute("text", "text");
		
		FoodItem foodItem1 = new FoodItem("name1", "content1", 1000);
		FoodItem foodItem2 = new FoodItem("name2", "content2", 2000);
		
		model.addAttribute("foodItem1", foodItem1);
		model.addAttribute("foodItem2", foodItem2);
		
		List<FoodItem> foods = new ArrayList<FoodItem>();
		foods.add(foodItem1);
		foods.add(foodItem2);
		
		model.addAttribute("foods", foods);
		
		return "sampleTL/javascript";
	}
	
	@GetMapping("/fragment")
	public String fragment() {
		
		return "sampleTL/fragment";
	}
}








