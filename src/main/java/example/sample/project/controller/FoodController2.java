package example.sample.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import example.sample.project.domain.FoodItem;
import example.sample.project.domain.FoodType;
import example.sample.project.domain.ShopCode;
import example.sample.project.repository.FoodRepository;
import example.sample.project.validation.FoodItemValidator;
import example.sample.project.validation.form.FoodItemNewForm;
import example.sample.project.validation.form.NewCheck;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/foods2")
public class FoodController2 { 
//	private final FoodRepository foodRepository = FoodRepository.getInstance();
	
	private final FoodRepository foodRepository;
	
//	private final FoodItemValidator foodItemValidator;
//	
//	@Autowired
//	public FoodController(FoodItemValidator foodItemValidator) {
//		this.foodItemValidator = foodItemValidator;
//	}
	
	@GetMapping
	// 전체 정보를 넘기는 것
	public String foods(Model model) {
		List<FoodItem> foodList = foodRepository.selectAll();
		
		model.addAttribute("foods2", foodList);
		
		for(FoodType ft : FoodType.values()) {
			log.info(ft.name());
		}
		
		return "foods2/foods"; // 페이지 정보 반환
	}
	@PostMapping("/food")
	public String food2(Model model, @RequestParam int foodId) {
		FoodItem foodItem = foodRepository.selectById(foodId);
		model.addAttribute("food", foodItem);
		
		return "foods2/food";
	}
	
	
	@GetMapping("/{foodId}")
	public String food(Model model, @PathVariable("foodId") int foodId) {
		FoodItem foodItem = foodRepository.selectById(foodId);
		// foodItem 을 찾아서 담아서 넘길 것이다.
		model.addAttribute("food", foodItem);
		
	
		
		return "foods2/food";
	}
	
	@GetMapping("/new")
	public String newFood(Model model) {
		model.addAttribute("foodItem", new FoodItem());
		return "foods2/new";
	}
	
	@PostMapping("/new")
	public String newFoodInsertModel(
//			@Validated @ModelAttribute("foodItem") FoodItemNewForm foodItemNewForm
//			@Validated(UpdateCheck.class) @ModelAttribute("foodItem") FoodItemNewForm foodItemNewForm
			@Validated(NewCheck.class) @ModelAttribute("foodItem") FoodItemNewForm foodItemNewForm
			,BindingResult bindingResult
			,RedirectAttributes rAttr) {
		
		log.info(foodItemNewForm.toString());
		
		log.info(bindingResult.getObjectName());
		log.info("{}", bindingResult.getTarget());
		
//		foodItemValidator.validate(foodItem, bindingResult);
		
		if(foodItemNewForm.getSoldout()) {
			bindingResult.reject("failureMsg", null);
		}
		
		if(bindingResult.hasErrors()) {
			log.info("bindingResult={}", bindingResult);
			return "foods2/new";
		}
		
		FoodItem insFoodItem = new FoodItem();
		insFoodItem.setId(foodItemNewForm.getId());
		insFoodItem.setItemName(foodItemNewForm.getItemName());
		insFoodItem.setContent(foodItemNewForm.getContent());
		insFoodItem.setPrice(foodItemNewForm.getPrice());
		
		foodRepository.insert(insFoodItem);
		
		rAttr.addAttribute("foodId", foodItemNewForm.getId());
		rAttr.addAttribute("test", "ok");
		return "redirect:/foods2/{foodId}";
		
	}
	
//	@PostMapping("/new")
//	public String newFoodInsertModel(
//			@Validated
//			@ModelAttribute("foodItem") FoodItemNewForm foodItemNewForm
////			@ModelAttribute FoodItem foodItem
////			, Model model
//			, BindingResult bindingResult
//			, RedirectAttributes rAttr) {
//		
//		log.info(foodItemNewForm.toString());
//		
//		log.info(bindingResult.getObjectName());
//		log.info("{}",bindingResult.getTarget());
//		
//		
////		foodRepository.insert(foodItem);
//		
//		ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required.foodItem.itemName");
//		
//		
//		if(!StringUtils.hasText(foodItemNewForm.getItemName())) {
//												//objectName				실패한 값								
////			bindingResult.addError(new FieldError("foodItem", "itemName", foodItem.getItemName(),false, new String[] {"required.foodItem.itemName"}, null,"아이템 이름은 필수입력"));
//			bindingResult.rejectValue("itemName", "required.foodItem.itemName", "default message()");
//		}
//		
//		if(!StringUtils.hasText(foodItemNewForm.getItemName())) {
//												// 객체명 		필드 			메세지
////			bindingResult.addError(new FieldError("foodItem", "content",foodItem.getContent() ,false, new String[] {"required.foodItem.itemName"}, null, "컨텐츠는 필수입력"));
//			bindingResult.rejectValue("content", "required.foodItem.content", "default message()");
//		}
//		
//		if(foodItem.getPrice() > 10000) {
////			bindingResult.addError(new FieldError("foodItem", "price", foodItem.getPrice(), false, new String[] {"max.foodItem.price"}, new Object[] {1, 10000}, "너무 비싸다 ~만원까지 상품만취급"));
//			bindingResult.rejectValue("price","max.foodItem.price", new Object[] {1, 10000, foodItem.getPrice()} , "default message()" );
//		}
//		
//	
//		if(foodItem.getSoldout()) {
//			bindingResult.reject("failureMsg", null);
//			// global메세지 처리
////			bindingResult.addError(new ObjectError("foodItem", new String[] {"failureMsg"}, null, "DefaultMessage"));
////			bindingResult.addError(new ObjectError("foodItem", "에러 발생"));
////			bindingResult.addError(new ObjectError("foodItem", "아차 오타!"));
//		}
//		
//		if(bindingResult.hasErrors()) {
//			log.info("binginResult={}", bindingResult);
//			return "foods2/new";
//		}
//		
////		if(!errors.isEmpty()) {
////			model.addAttribute("errors", errors);	// 담고
////			return "foods2/new";
////		}
//			
//		foodRepository.insert(foodItem);
//		
//		rAttr.addAttribute("foodId", foodItem.getId());
//		rAttr.addAttribute("test", "ok");
//		
////		return "foods2/food";
////		return "redirect:/foods/" + foodItem.getId();
//		return "redirect:/foods2/{foodId}";
//	}
	
	// 더 간단하게 만들 예정~
	@PostMapping("/new_old3")
	public String newFoodInsertOld3(
			@ModelAttribute FoodItem foodItem
//			, Model model
			, BindingResult bindingResult
			, RedirectAttributes rAttr) {
		
		log.info(foodItem.toString());
		
		foodRepository.insert(foodItem);
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
												//objectName				실패한 값								
			bindingResult.addError(new FieldError("foodItem", "itemName", foodItem.getItemName(),false, new String[] {"required.foodItem.itemName"}, null,"아이템 이름은 필수입력"));
		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
												// 객체명 		필드 			메세지
			bindingResult.addError(new FieldError("foodItem", "content",foodItem.getContent() ,false, new String[] {"required.foodItem.itemName"}, null, "컨텐츠는 필수입력"));
		}
		
		if(foodItem.getPrice() > 10000) {
			bindingResult.addError(new FieldError("foodItem", "price", foodItem.getPrice(), false, new String[] {"max.foodItem.price"}, new Object[] {1, 10000}, "너무 비싸다 ~만원까지 상품만취급"));
		}
		
	
		if(foodItem.getSoldout()) {
			bindingResult.addError(new ObjectError("foodItem", new String[] {"failureMsg"}, null, "DefaultMessage"));
			bindingResult.addError(new ObjectError("foodItem", "에러 발생"));
			bindingResult.addError(new ObjectError("foodItem", "아차 오타!"));
		}
		
		if(bindingResult.hasErrors()) {
			log.info("binginResult={}", bindingResult);
			return "foods2/new";
		}
		
//		if(!errors.isEmpty()) {
//			model.addAttribute("errors", errors);	// 담고
//			return "foods/new";
//		}
			
		foodRepository.insert(foodItem);
		
		rAttr.addAttribute("foodId", foodItem.getId());
		rAttr.addAttribute("test", "ok");
		
//		return "foods/food";
//		return "redirect:/foods/" + foodItem.getId();
		return "redirect:/foods2/{foodId}";
	}
	
	// 더 간단하게 만들 예정~
	@PostMapping("/new_old2")
	public String newFoodInsertOld2(
			@ModelAttribute FoodItem foodItem
//			, Model model
			, BindingResult bindingResult
			, RedirectAttributes rAttr) {
		
		log.info(foodItem.toString());
		
		foodRepository.insert(foodItem);
//		model.addAttribute("food", foodItem);
//		Map<String, String> options = new HashMap<>();
//		options.put("1번", "탄수화물");
//		options.put("2번", "단백질");
//		options.put("3번", "지방");
//		model.addAttribute("options", options);
		
		
		// 검증 과정
//		if(foodItem.getItemName() == null || foodItem.getItemName().trim().equals("")) {
//			errors.put("itemName", "아이템 이름은 필수 입력해주세요");
//			model.addAttribute("errors", errors);
//			return "foods/new";
//		}
		
//		if(!StringUtils.hasText(foodItem.getItemName())) {
//			return "foods/new";
//		}
		
//		if(!StringUtils.hasText(foodItem.getContent())) {
//			errors.put("content", "컨텐츠 필수 입력");
//			model.addAttribute("errors", errors);
//			return "foods/new";
//		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
			bindingResult.addError(new FieldError("foodItem", "itemName", "아이템 이름은       입력"));
		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
												// 객체명 		필드 			메세지
			bindingResult.addError(new FieldError("foodItem", "content", "컨텐츠는 필수입력"));
		}
		
		if(foodItem.getPrice() > 10000) {
			bindingResult.addError(new FieldError("foodItem", "price", "너무 비싸다 ~만원까지 상품만취급"));
		}
		
	
		if(foodItem.getSoldout()) {
			bindingResult.addError(new ObjectError("foodItem", "똑바로 입력하세요."));
			bindingResult.addError(new ObjectError("foodItem", "에러 발생"));
			bindingResult.addError(new ObjectError("foodItem", "아차 오타!"));
		}
		
		if(bindingResult.hasErrors()) {
			log.info("binginResult={}", bindingResult);
			return "foods2/new";
		}
		
//		if(!errors.isEmpty()) {
//			model.addAttribute("errors", errors);	// 담고
//			return "foods/new";
//		}
			
		foodRepository.insert(foodItem);
		
		rAttr.addAttribute("foodId", foodItem.getId());
		rAttr.addAttribute("test", "ok");
		
//		return "foods/food";
//		return "redirect:/foods/" + foodItem.getId();
		return "redirect:/foods2/{foodId}";
	}
	
	// 더 간단하게 만들 예정~
	@PostMapping("/new_old")
	public String newFoodInsertOld(
			@ModelAttribute FoodItem foodItem
			, Model model
			, RedirectAttributes rAttr) {
		
		log.info(foodItem.toString());
		
		foodRepository.insert(foodItem);
//		model.addAttribute("food", foodItem);
//		Map<String, String> options = new HashMap<>();
//		options.put("1번", "탄수화물");
//		options.put("2번", "단백질");
//		options.put("3번", "지방");
//		model.addAttribute("options", options);
		
		Map<String, String> errors = new HashMap<>();
		
		// 검증 과정
//		if(foodItem.getItemName() == null || foodItem.getItemName().trim().equals("")) {
//			errors.put("itemName", "아이템 이름은 필수 입력해주세요");
//			model.addAttribute("errors", errors);
//			return "foods/new";
//		}
		
//		if(!StringUtils.hasText(foodItem.getItemName())) {
//			return "foods/new";
//		}
		
//		if(!StringUtils.hasText(foodItem.getContent())) {
//			errors.put("content", "컨텐츠 필수 입력");
//			model.addAttribute("errors", errors);
//			return "foods/new";
//		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
			errors.put("itemName", "아이템 이름은 필수입력");
		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
			errors.put("content", "컨텐츠는 필수입력");
		}
		
		if(foodItem.getPrice() > 10000) {
			errors.put("price", "너무 비싸다 ~만원까지 상품만취급");
		}
		
		if(foodItem.getSoldout()) {
			errors.put("globalError", "똑바로 입력하세요.");
		}
		
		if(!errors.isEmpty()) {
			model.addAttribute("errors", errors);	// 담고
			return "foods2/new";
		}
			
		foodRepository.insert(foodItem);
		
		rAttr.addAttribute("foodId", foodItem.getId());
		rAttr.addAttribute("test", "ok");
		
//		return "foods/food";
//		return "redirect:/foods/" + foodItem.getId();
		return "redirect:/foods2/{foodId}";
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
		
		return "foods2/update.html";
	}
	
	@PostMapping("/update/{foodId}")
	public String updateFoodProcess(Model model
			, @PathVariable("foodId") int foodId
			, @ModelAttribute FoodItem foodItem) {
		log.info(foodItem.toString());	// 넘어온거 보기
//		log.info("/update/{}", foodId);
		foodRepository.update(foodId, foodItem);
		
		// 여기서 바로 foods/id에 해당하는 foods/food 페이지를 보여주면서 <- food 객체를 주입
		// 1번.
//		model.addAttribute("food",foodItem);
//		return "foods/update.html";
		// 2번.
		// food 상세정보를 보여주는 경로가 이미 존재. -> 이미 존재하는 메소드를 활용
		return "redirect:/foods2/{foodId}";
	}
	
	
	@ModelAttribute("options")
	public Map<String, String> options(){
//		Map<String, String> options = new HashMap<>();
		Map<String, String> options = new LinkedHashMap<>();
		
		options.put("1번", "탄수화물");
		options.put("2번", "단백질");
		options.put("3번", "지방");
		
		return options;
	}
	
   @ModelAttribute("foodTypes")
   public FoodType[] FoodTypes() {
      return FoodType.values();
   }
	
   @ModelAttribute("shopCodes")
   public List<ShopCode> shopCodes(){
	   List<ShopCode> shopCodes = new ArrayList<>();
	   shopCodes.add(new ShopCode("A", "청정"));
	   shopCodes.add(new ShopCode("B", "인공"));
	   shopCodes.add(new ShopCode("C", "재활용"));
	   
	   return shopCodes;
   }
	
	@PostConstruct
	public void insertInit() {
		FoodItem foodItem1 = new FoodItem("김밥", "돈까스", 3500);
		foodRepository.insert(foodItem1);
		
		FoodItem foodItem2 = new FoodItem("우동", "김치", 4000);
		foodRepository.insert(foodItem2);
		
	}
}
