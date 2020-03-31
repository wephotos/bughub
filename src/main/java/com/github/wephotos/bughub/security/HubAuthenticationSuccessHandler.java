package com.github.wephotos.bughub.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.RestObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录成功处理
 * @author Dell-Aaron
 *
 */
@Slf4j
@Component
public class HubAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User principal = (User)authentication.getPrincipal();
		log.info("principal:{}", principal);
		response.setHeader("Authorization", principal.getUsername());
		Cookie cookie = new Cookie("Authorization", principal.getUsername());
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		//认证信息保存至当前线程
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //登录后可重定向，可用请求头判断AJAX请求
		if(BugUtils.isXMLHttpRequest(request)) {
			RestObject value = RestObject.builder().msg("success").build();
			BugUtils.responseJson(value, response);
		}else {
			redirectStrategy.sendRedirect(request, response, "/");
		}
	}

}
