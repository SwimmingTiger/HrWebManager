package dlnu.web.hrmanager.login.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminLoginFilter implements Filter {
	private static String loginUrl = "/hrmanager/login/login";
	
	private boolean loginCheck(HttpSession session) {
		boolean isLogin = false;
		
		if (session.getAttribute("loginType") != null) {
			int loginType = (int) session.getAttribute("loginType");
			
			if (loginType == 1) {
				isLogin = true;
			}
		}
		
		return isLogin;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String path = request.getRequestURI();
		
		boolean pass = false;
		
		if (loginUrl.equals(path)) {
			pass = true;
		} else {
			pass = loginCheck(session);
		}
		
		if (pass) {
			chain.doFilter(servletRequest, servletResponse);
		} else {
			response.sendRedirect(loginUrl);
		}
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}
}
