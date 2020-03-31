package com.github.wephotos.bughub.security;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * CAS配置
 * 
 * @author TQ
 *
 */
//@Configuration
//@EnableWebSecurity
public class CasSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Value("${security.cas.server.url}")
	private String casServerUrl;
	
	@Value("${security.cas.server.login.url}")
	private String casServerLoginUrl;
	
	@Value("${security.cas.server.logout.url}")
	private String casServerLogoutUrl;
	
	@Value("${security.server.url}")
	private String serverUrl;
	
	@Value("${security.server.login.url}")
	private String loginUrl;
	
	@Value("${security.server.logout.url}")
	private String logoutUrl;

	@Autowired
	private HubUserDetailsService hubUserDetailsService;

	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setService(serverUrl + loginUrl);
		serviceProperties.setAuthenticateAllArtifacts(true);
		return serviceProperties;
	}

	@Bean
	public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
		CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
		casAuthenticationEntryPoint.setLoginUrl(casServerLoginUrl);
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
		return casAuthenticationEntryPoint;
	}

	@Bean
	public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
		casAuthenticationFilter.setAuthenticationManager(authenticationManager());
		casAuthenticationFilter.setFilterProcessesUrl(loginUrl);
		//casAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
		return casAuthenticationFilter;
	}

	@Bean
	public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
		return new Cas20ServiceTicketValidator(casServerUrl);
	}

	@Bean
	public CasAuthenticationProvider casAuthenticationProvider() {
		CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
		casAuthenticationProvider.setUserDetailsService(hubUserDetailsService);
		casAuthenticationProvider.setServiceProperties(serviceProperties());
		casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
		casAuthenticationProvider.setKey("casAuthenticationProviderKey");
		return casAuthenticationProvider;
	}

	@Bean
	public SingleSignOutFilter singleSignOutFilter() {
		SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
		singleSignOutFilter.setCasServerUrlPrefix(casServerUrl);
		singleSignOutFilter.setIgnoreInitConfiguration(true);
		return singleSignOutFilter;
	}

	@Bean
	public LogoutFilter casLogoutFilter() {
		String logoutSuccessUrl = String.format("%s?service=%s", casServerLogoutUrl, serverUrl);
		LogoutFilter logoutFilter = new LogoutFilter(logoutSuccessUrl, new SecurityContextLogoutHandler());
		logoutFilter.setFilterProcessesUrl(logoutUrl);
		return logoutFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(casAuthenticationProvider());
		super.configure(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.headers()
			.frameOptions()
			.sameOrigin()
			.xssProtection()
			.block(true);

		http.authorizeRequests()// 配置安全策略
			.antMatchers("/resources/**", "/login").permitAll()
			.antMatchers("/hub-user/addOrUpdate", "/hub-user/delete/*", "/project/delete/*").hasRole("ADMIN")
			.anyRequest().authenticated().and().httpBasic();
		
		http.exceptionHandling()
			.authenticationEntryPoint(casAuthenticationEntryPoint())
			.and()
			.addFilter(casAuthenticationFilter())
			.addFilterBefore(casLogoutFilter(), LogoutFilter.class)
			.addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
	}

}