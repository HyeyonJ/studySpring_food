package example.sample.project.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import example.sample.project.session.SessionVar;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LoginFilter implements Filter {
	
	private static final String[] whiteList = {"/", "/login", "/members/new", "/logout"};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		log.info("LoginFilter start");
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		
		HttpServletResponse resp = (HttpServletResponse)response;
		
		// 로그인이 안되어있으면 / 페이지로 이동
		// 안되어있어서 보내야하는 경우
		
		if(!PatternMatchUtils.simpleMatch(whiteList, uri)) {
			// 로그인했는지 체크
			HttpSession session = req.getSession(false);
			if(session == null || session.getAttribute(SessionVar.LOGIN_MEMBER) == null) {
			// 로그인이 안된 케이스
				log.info("로그인 없이 접근 시도{}", uri);
				resp.sendRedirect("/login");
				
				return;	// 종료
			}
		}
		chain.doFilter(request, response);
	}
}


