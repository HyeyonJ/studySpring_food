package example.sample.project.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import example.sample.project.session.SessionVar;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI();
		log.info("LoginInterceptor preHandle {}", uri);
		
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute(SessionVar.LOGIN_MEMBER ) == null ) {
			log.info("로그인 없이 접근 시도 {}", uri);
			response.sendRedirect("/login?redirectURL="  + uri);
			return false; //멈추기
		}
		return true;
	}
}
