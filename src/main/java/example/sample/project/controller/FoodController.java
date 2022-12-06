package example.sample.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import example.sample.project.domain.FoodItem;
import example.sample.project.repository.FoodRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/foods")
public class FoodController {
	private final FoodRepository foodRepository = FoodRepository.getInstance();
	
	@GetMapping
	// 전체 정보를 넘기는 것
	public String foods(Model model) {
		List<FoodItem> foodList = foodRepository.selectAll();
		
		model.addAttribute("foods", foodList);
		
		return "foods/foods"; // 페이지 정보 반환
	}
	
	
	@GetMapping("/{foodId}")
	public String food(Model model, @PathVariable("foodId") int foodId) {
		FoodItem foodItem = foodRepository.selectById(foodId);
		// foodItem 을 찾아서 담아서 넘길 것이다.
		model.addAttribute("food", foodItem);
		
		return "foods/food";
	}
	
	@GetMapping("/new")
	public String newFood(Model model) {
		model.addAttribute("foodItem", new FoodItem());
		return "foods/new";
	}
	
	// 더 간단하게 만들 예정~
	@PostMapping("/new")
	public String newFoodInsert(
			@ModelAttribute FoodItem foodItem
//			, Model model
			, RedirectAttributes rAttr) {
		
		log.info(foodItem.toString());
		
		foodRepository.insert(foodItem);
//		model.addAttribute("food", foodItem);
		
		rAttr.addAttribute("foodId", foodItem.getId());
		rAttr.addAttribute("test", "ok");
		
//		return "foods/food";
//		return "redirect:/foods/" + foodItem.getId();
		return "redirect:/foods/{foodId}";
	}
	
//	// 데이터가 담겨서 들어 올 것이다. 화면에 그려주는 애들을 전달을 해줘야해서 모델을 받아줘야함.
//	@PostMapping("/new")
//	public String newFoodInsert(@RequestParam("itemName") String itemName
//			, @RequestParam("content") String content
//			, @RequestParam("price") int price
//			, Model model) {
//	
//		FoodItem foodItem = new FoodItem(itemName, content, price);
//		foodRepository.insert(foodItem);
//		
//		model.addAttribute("food", foodItem);
//		
//		return "foods/food";
//	}
	
	
	@GetMapping("/update/{foodId}")
	public String updateFood(Model model, @PathVariable("foodId") int foodId) {
		FoodItem foodItem = foodRepository.selectById(foodId);
		model.addAttribute("food",foodItem);
		
		return "foods/update.html";
	}
	
	@PostMapping("/update/{foodId}")
	public String updateFoodProcess(Model model
			, @PathVariable("foodId") int foodId
			, @ModelAttribute FoodItem foodItem) {
		log.info(foodItem.toString());	// 넘어온거 보기
//		log.info("/update/{}", foodId);
		foodRepository.update(foodId, foodItem);
		model.addAttribute("food",foodItem);
		
		// 여기서 바로 foods/id에 해당하는 foods/food 페이지를 보여주면서 <- food 객체를 주입
		// 1번.
//		model.addAttribute("food",foodItem);
//		return "foods/update.html";
		// 2번.
		// food 상세정보를 보여주는 경로가 이미 존재. -> 이미 존재하는 메소드를 활용
		return "redirect:/foods/{foodId}";
	}
	
	
	@ModelAttribute("options")
	public Map<String, String> options(){
		Map<String, String> options = new HashMap<>();
		
		options.put("1번", "탄수화물");
		options.put("2번", "단백질");
		options.put("3번", "지방");
		
		return options;
	}
	
	
	@PostConstruct
	public void insertInit() {
		FoodItem foodItem1 = new FoodItem("김밥", "돈까스", 3500);
		foodRepository.insert(foodItem1);
		
		FoodItem foodItem2 = new FoodItem("우동", "김치", 4000);
		foodRepository.insert(foodItem2);
		
	}
}
