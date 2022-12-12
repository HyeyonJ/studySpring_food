package example.sample.project.controller.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor implements HandlerInterceptor{

	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri = request.getRequestURI();
		String test = "test text";
		// 					이름 			벨류
		request.setAttribute("test", "test text");
		
		log.info("LogIntercepter preHandles {}, {}", uri, test);

		return true;	// 진행
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
		log.info("LogIntercepter postHandle {}");
		log.info("test{}", request.getAttribute("test"));
		log.info("ModelAndView {}", modelAndView);
	}
	// try catch 부분 중 finally 끝나고 무조건 실행하는 부분
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		
		log.info("LogIntercepter afterCompletion");
		log.info("test{}", request.getAttribute("test"));
		if(ex != null) {
			log.error("LogIntercepter afterCompletion Exception", ex);
		}
		
	}
}
