package example.sample.project.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeConroller {

	@GetMapping
	public String home() {
		return "home";
	}
}
