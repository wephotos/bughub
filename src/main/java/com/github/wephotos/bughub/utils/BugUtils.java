package com.github.wephotos.bughub.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.service.HubUserService;

/**
 * 工具类
 * @author Dell-Aaron
 *
 */
@Component
public final class BugUtils {
	
	private static ObjectMapper objectMapper;
	
	private static HubUserService hubUserService;
	
	@Autowired
	public void setHubUserService(HubUserService hubUserService) {
		BugUtils.hubUserService = hubUserService;
	}
	
	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		BugUtils.objectMapper = objectMapper;
	}

	private BugUtils() {
		
	}
	
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static Timestamp timestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static boolean isXMLHttpRequest(HttpServletRequest request) {
		String xRequestedWith = request.getHeader("X-Requested-With");
		return xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest");
	}
	
	/**
	 * 写出JSON数据到响应流中
	 * @param value
	 * @param response
	 * @throws IOException
	 */
	public static void responseJson(Object value, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		objectMapper.writeValue(response.getOutputStream(), value);
	}
	
	/**
	 * 获取当前认证用户
	 * @return
	 */
	public static HubUser getAuthenticationUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User principal = (User)authentication.getPrincipal();
		return hubUserService.selectByAccount(principal.getUsername());
	}
}
