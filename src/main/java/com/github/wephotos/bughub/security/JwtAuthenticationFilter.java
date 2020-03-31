package com.github.wephotos.bughub.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * JWT认证过滤器
 * @author Dell-Aaron
 *
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private HubUserDetailsService hubUserDetailsService;
	
	private RequestMatcher requiresResourcesRequestMatcher;
	
	public JwtAuthenticationFilter() {
		this.requiresResourcesRequestMatcher = new AntPathRequestMatcher("/resources/**");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		if(requiresResources(request, response)) {
			chain.doFilter(request, response);
			return;
		}
		String token = "";
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("Authorization".equals(cookie.getName())) {
					token = cookie.getValue();
				}
			}
		}
		log.info("path:{}", request.getRequestURI());
		if(StringUtils.isNotBlank(token)) {
			log.info("token:{}", token);
			//使用用户名模拟
			UserDetails user = hubUserDetailsService.loadUserByUsername(token);
			// 将用户信息存入 authentication，方便后续校验
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}

	protected boolean requiresResources(HttpServletRequest request,
			HttpServletResponse response) {
		return requiresResourcesRequestMatcher.matches(request);
	}
}
