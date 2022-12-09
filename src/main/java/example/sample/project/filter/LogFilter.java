package example.sample.project.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request
						, ServletResponse response
						, FilterChain chain)
	throws IOException , ServletException { 
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		
		log.info("Request {}", uri);
		chain.doFilter(request, response);
		log.info("Response", uri);
	}
	
	public void init(FilterConfig filterConfig) 
			throws ServletException {
		log.info("init");
	}

	public void destroy() {
		log.info("destroy");
	}
}
