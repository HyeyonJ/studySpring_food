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
import example.sample.project.repository.FoodItemRepository;
import example.sample.project.repository.mybatis.MybatisFoodItemRepository;
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
	
	private final MybatisFoodItemRepository foodRepository;
	
//	private final FoodItemValidator foodItemValidator;
//	
//	@Autowired
//	public FoodController(FoodItemValidator foodItemValidator) {
//		this.foodItemValidator = foodItemValidator;
//	}
	
	@GetMapping
	// ?????? ????????? ????????? ???
	public String foods(Model model) {
		List<FoodItem> foodList = foodRepository.selectAll();
		
		model.addAttribute("foods2", foodList);
		
		for(FoodType ft : FoodType.values()) {
			log.info(ft.name());
		}
		
		return "foods2/foods"; // ????????? ?????? ??????
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
		// foodItem ??? ????????? ????????? ?????? ?????????.
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
//												//objectName				????????? ???								
////			bindingResult.addError(new FieldError("foodItem", "itemName", foodItem.getItemName(),false, new String[] {"required.foodItem.itemName"}, null,"????????? ????????? ????????????"));
//			bindingResult.rejectValue("itemName", "required.foodItem.itemName", "default message()");
//		}
//		
//		if(!StringUtils.hasText(foodItemNewForm.getItemName())) {
//												// ????????? 		?????? 			?????????
////			bindingResult.addError(new FieldError("foodItem", "content",foodItem.getContent() ,false, new String[] {"required.foodItem.itemName"}, null, "???????????? ????????????"));
//			bindingResult.rejectValue("content", "required.foodItem.content", "default message()");
//		}
//		
//		if(foodItem.getPrice() > 10000) {
////			bindingResult.addError(new FieldError("foodItem", "price", foodItem.getPrice(), false, new String[] {"max.foodItem.price"}, new Object[] {1, 10000}, "?????? ????????? ~???????????? ???????????????"));
//			bindingResult.rejectValue("price","max.foodItem.price", new Object[] {1, 10000, foodItem.getPrice()} , "default message()" );
//		}
//		
//	
//		if(foodItem.getSoldout()) {
//			bindingResult.reject("failureMsg", null);
//			// global????????? ??????
////			bindingResult.addError(new ObjectError("foodItem", new String[] {"failureMsg"}, null, "DefaultMessage"));
////			bindingResult.addError(new ObjectError("foodItem", "?????? ??????"));
////			bindingResult.addError(new ObjectError("foodItem", "?????? ??????!"));
//		}
//		
//		if(bindingResult.hasErrors()) {
//			log.info("binginResult={}", bindingResult);
//			return "foods2/new";
//		}
//		
////		if(!errors.isEmpty()) {
////			model.addAttribute("errors", errors);	// ??????
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
	
	// ??? ???????????? ?????? ??????~
	@PostMapping("/new_old3")
	public String newFoodInsertOld3(
			@ModelAttribute FoodItem foodItem
//			, Model model
			, BindingResult bindingResult
			, RedirectAttributes rAttr) {
		
		log.info(foodItem.toString());
		
		foodRepository.insert(foodItem);
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
												//objectName				????????? ???								
			bindingResult.addError(new FieldError("foodItem", "itemName", foodItem.getItemName(),false, new String[] {"required.foodItem.itemName"}, null,"????????? ????????? ????????????"));
		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
												// ????????? 		?????? 			?????????
			bindingResult.addError(new FieldError("foodItem", "content",foodItem.getContent() ,false, new String[] {"required.foodItem.itemName"}, null, "???????????? ????????????"));
		}
		
		if(foodItem.getPrice() > 10000) {
			bindingResult.addError(new FieldError("foodItem", "price", foodItem.getPrice(), false, new String[] {"max.foodItem.price"}, new Object[] {1, 10000}, "?????? ????????? ~???????????? ???????????????"));
		}
		
	
		if(foodItem.getSoldout()) {
			bindingResult.addError(new ObjectError("foodItem", new String[] {"failureMsg"}, null, "DefaultMessage"));
			bindingResult.addError(new ObjectError("foodItem", "?????? ??????"));
			bindingResult.addError(new ObjectError("foodItem", "?????? ??????!"));
		}
		
		if(bindingResult.hasErrors()) {
			log.info("binginResult={}", bindingResult);
			return "foods2/new";
		}
		
//		if(!errors.isEmpty()) {
//			model.addAttribute("errors", errors);	// ??????
//			return "foods/new";
//		}
			
		foodRepository.insert(foodItem);
		
		rAttr.addAttribute("foodId", foodItem.getId());
		rAttr.addAttribute("test", "ok");
		
//		return "foods/food";
//		return "redirect:/foods/" + foodItem.getId();
		return "redirect:/foods2/{foodId}";
	}
	
	// ??? ???????????? ?????? ??????~
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
//		options.put("1???", "????????????");
//		options.put("2???", "?????????");
//		options.put("3???", "??????");
//		model.addAttribute("options", options);
		
		
		// ?????? ??????
//		if(foodItem.getItemName() == null || foodItem.getItemName().trim().equals("")) {
//			errors.put("itemName", "????????? ????????? ?????? ??????????????????");
//			model.addAttribute("errors", errors);
//			return "foods/new";
//		}
		
//		if(!StringUtils.hasText(foodItem.getItemName())) {
//			return "foods/new";
//		}
		
//		if(!StringUtils.hasText(foodItem.getContent())) {
//			errors.put("content", "????????? ?????? ??????");
//			model.addAttribute("errors", errors);
//			return "foods/new";
//		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
			bindingResult.addError(new FieldError("foodItem", "itemName", "????????? ?????????       ??????"));
		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
												// ????????? 		?????? 			?????????
			bindingResult.addError(new FieldError("foodItem", "content", "???????????? ????????????"));
		}
		
		if(foodItem.getPrice() > 10000) {
			bindingResult.addError(new FieldError("foodItem", "price", "?????? ????????? ~???????????? ???????????????"));
		}
		
	
		if(foodItem.getSoldout()) {
			bindingResult.addError(new ObjectError("foodItem", "????????? ???????????????."));
			bindingResult.addError(new ObjectError("foodItem", "?????? ??????"));
			bindingResult.addError(new ObjectError("foodItem", "?????? ??????!"));
		}
		
		if(bindingResult.hasErrors()) {
			log.info("binginResult={}", bindingResult);
			return "foods2/new";
		}
		
//		if(!errors.isEmpty()) {
//			model.addAttribute("errors", errors);	// ??????
//			return "foods/new";
//		}
			
		foodRepository.insert(foodItem);
		
		rAttr.addAttribute("foodId", foodItem.getId());
		rAttr.addAttribute("test", "ok");
		
//		return "foods/food";
//		return "redirect:/foods/" + foodItem.getId();
		return "redirect:/foods2/{foodId}";
	}
	
	// ??? ???????????? ?????? ??????~
	@PostMapping("/new_old")
	public String newFoodInsertOld(
			@ModelAttribute FoodItem foodItem
			, Model model
			, RedirectAttributes rAttr) {
		
		log.info(foodItem.toString());
		
		foodRepository.insert(foodItem);
//		model.addAttribute("food", foodItem);
//		Map<String, String> options = new HashMap<>();
//		options.put("1???", "????????????");
//		options.put("2???", "?????????");
//		options.put("3???", "??????");
//		model.addAttribute("options", options);
		
		Map<String, String> errors = new HashMap<>();
		
		// ?????? ??????
//		if(foodItem.getItemName() == null || foodItem.getItemName().trim().equals("")) {
//			errors.put("itemName", "????????? ????????? ?????? ??????????????????");
//			model.addAttribute("errors", errors);
//			return "foods/new";
//		}
		
//		if(!StringUtils.hasText(foodItem.getItemName())) {
//			return "foods/new";
//		}
		
//		if(!StringUtils.hasText(foodItem.getContent())) {
//			errors.put("content", "????????? ?????? ??????");
//			model.addAttribute("errors", errors);
//			return "foods/new";
//		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
			errors.put("itemName", "????????? ????????? ????????????");
		}
		
		if(!StringUtils.hasText(foodItem.getItemName())) {
			errors.put("content", "???????????? ????????????");
		}
		
		if(foodItem.getPrice() > 10000) {
			errors.put("price", "?????? ????????? ~???????????? ???????????????");
		}
		
		if(foodItem.getSoldout()) {
			errors.put("globalError", "????????? ???????????????.");
		}
		
		if(!errors.isEmpty()) {
			model.addAttribute("errors", errors);	// ??????
			return "foods2/new";
		}
			
		foodRepository.insert(foodItem);
		
		rAttr.addAttribute("foodId", foodItem.getId());
		rAttr.addAttribute("test", "ok");
		
//		return "foods/food";
//		return "redirect:/foods/" + foodItem.getId();
		return "redirect:/foods2/{foodId}";
	}
	
//	// ???????????? ????????? ?????? ??? ?????????. ????????? ???????????? ????????? ????????? ??????????????? ????????? ???????????????.
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
		log.info(foodItem.toString());	// ???????????? ??????
//		log.info("/update/{}", foodId);
		foodRepository.update(foodId, foodItem);
		
		// ????????? ?????? foods/id??? ???????????? foods/food ???????????? ??????????????? <- food ????????? ??????
		// 1???.
//		model.addAttribute("food",foodItem);
//		return "foods/update.html";
		// 2???.
		// food ??????????????? ???????????? ????????? ?????? ??????. -> ?????? ???????????? ???????????? ??????
		return "redirect:/foods2/{foodId}";
	}
	
	
	@ModelAttribute("options")
	public Map<String, String> options(){
//		Map<String, String> options = new HashMap<>();
		Map<String, String> options = new LinkedHashMap<>();
		
		options.put("1???", "????????????");
		options.put("2???", "?????????");
		options.put("3???", "??????");
		
		return options;
	}
	
   @ModelAttribute("foodTypes")
   public FoodType[] FoodTypes() {
      return FoodType.values();
   }
	
   @ModelAttribute("shopCodes")
   public List<ShopCode> shopCodes(){
	   List<ShopCode> shopCodes = new ArrayList<>();
	   shopCodes.add(new ShopCode("A", "??????"));
	   shopCodes.add(new ShopCode("B", "??????"));
	   shopCodes.add(new ShopCode("C", "?????????"));
	   
	   return shopCodes;
   }
	
//	@PostConstruct
	public void insertInit() {
		FoodItem foodItem1 = new FoodItem("??????", "?????????", 3500);
		foodRepository.insert(foodItem1);
		
		FoodItem foodItem2 = new FoodItem("??????", "??????", 4000);
		foodRepository.insert(foodItem2);
		
	}
}
