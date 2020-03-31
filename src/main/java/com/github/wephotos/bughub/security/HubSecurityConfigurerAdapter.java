package com.github.wephotos.bughub.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.RestObject;

/**
 * 认证配置
 * 
 * @author Dell-Aaron
 *
 */
@EnableWebSecurity
public class HubSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private HubUserDetailsService hubUserDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private HubAuthenticationSuccessHandler successHandler; 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers()
		.frameOptions().sameOrigin()
		.and().csrf().disable().authorizeRequests()
		.antMatchers("/resources/**", "/views/login.html", "/login").permitAll()
		.antMatchers("/hub-user/addOrUpdate", 
				     "/hub-user/delete/*", 
				     "/project/delete/*").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().loginProcessingUrl("/login").successHandler(successHandler)
		.and().logout().logoutSuccessUrl("/views/login.html").deleteCookies("Authorization")
		.and().sessionManagement().disable().httpBasic()
		.and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling().authenticationEntryPoint((request, response, err) -> {
			if(BugUtils.isXMLHttpRequest(request)) {
				RestObject value = RestObject.builder()
						.code(response.getStatus())
						.msg(err.getMessage()).build();
				BugUtils.responseJson(value, response);
			}else {
				request.getRequestDispatcher("/views/login.html").forward(request, response);
			}
		}).accessDeniedHandler((request, response, err) -> {
			if(BugUtils.isXMLHttpRequest(request)) {
				RestObject value = RestObject.builder()
						.code(response.getStatus())
						.msg(err.getMessage()).build();
				BugUtils.responseJson(value, response);
			}else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
			}
		});
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(hubUserDetailsService).passwordEncoder(new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.equals(encodedPassword);
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
		});
	}
}
