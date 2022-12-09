package example.sample.project.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {
	
	public static final String SESSION_COOKIE_NAME = "tempSessionId";
	// 자체적으로 관리하는 세션은 만들고
	private Map<String, Object> sessionMap = new HashMap<String, Object>();

	public void create(Object object, HttpServletResponse resp) {
		String sessionId = UUID.randomUUID().toString();
		sessionMap.put(sessionId, object);
		
		Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		resp.addCookie(cookie);
	}
	
	public void remove(HttpServletRequest req) {
		Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
		if(cookie != null) {
		sessionMap.remove(cookie.getValue());
		}
	}
	
	public Object getSessionObject(HttpServletRequest req) {
		// 쿠키 정보의 벨류 값 읽어서 그 벨류에 맞는 키를 가진 오브젝트를 가지고 와야 함(map안에 있는거)
		// req -> cookie -> tempSessionId(name) -> value(UUID)
		// value(UUID) sessionMap<value(UUID), Member)
		Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
		if(cookie != null) {
			return sessionMap.get(cookie.getValue());
		}
		return null;
	}
	
	public Cookie findCookie(HttpServletRequest req, String cookieName) {
		if (req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {
				if (cookie.getName().equals(cookieName)) {
					return cookie;	// 리턴 타입이 쿠키니까 쿠키를 리턴함
				}
			}
		}
		return null;	// 못찾으면
	}
}
