package example.sample.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import example.sample.project.interceptor.LogInterceptor;
import example.sample.project.interceptor.LoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	public void addInterceptors(InterceptorRegistry registry) {
//		// log
//		registry.addInterceptor(new LogInterceptor()).order(1).addPathPatterns("/**").excludePathPatterns("/css/**", "/js/**");
//		// login
//		registry.addInterceptor(new LoginInterceptor()).order(2).addPathPatterns("/**").excludePathPatterns("/", "/login", "/logout", "/members/new");
	}
	
}
