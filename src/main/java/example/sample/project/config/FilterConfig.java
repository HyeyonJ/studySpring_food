package example.sample.project.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import example.sample.project.filter.LogFilter;
import jakarta.servlet.Filter;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<Filter> logFilter(){
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
		filterRegistrationBean.setFilter(new LogFilter());
		filterRegistrationBean.setOrder(1);	// 로그가 여러개 있을 때 순서
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	
	}
	
	@Bean
	public FilterRegistrationBean<Filter> loginFilter(){
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
		filterRegistrationBean.setFilter(new LogFilter());
		filterRegistrationBean.setOrder(2);	// 로그가 여러개 있을 때 순서
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	
	}
	
}
