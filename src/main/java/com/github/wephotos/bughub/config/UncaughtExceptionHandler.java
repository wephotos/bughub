package com.github.wephotos.bughub.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.wephotos.bughub.utils.RestObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 * @author Dell-Aaron
 *
 */
@Slf4j
@RestControllerAdvice
public class UncaughtExceptionHandler {
	static final String CLIENT_ABORT_EXCEPTION_NAME = "ClientAbortException";
	/**
	 * 异常处理
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public RestObject uncaughtException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		// 忽略客户端导致的异常
		String simpleName = ex.getClass().getSimpleName();
		if (CLIENT_ABORT_EXCEPTION_NAME.equals(simpleName)) {
			return null;
		}
		log.error("系统错误", ex);
		return RestObject.builder().code(500).msg(ex.getMessage()).build();
	}
}
