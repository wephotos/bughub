package com.github.wephotos.bughub.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.RestObject;

import lombok.extern.slf4j.Slf4j;

/**
 * <b>描述:</b>
 *
 * <p>首页</p>
 *
 * @version 1.0
 * @author Aaron.tian
 * @Date 2019年8月2日下午1:41:43
 * @since JDK1.8
 */
@Slf4j
@Controller
public class WebController {

	/**
	 * 首页
	 * @return
	 */
	@GetMapping("/")
	public String index() {
		log.info("进入首页");
		return "/views/index.html";
	}

	@ResponseBody
	@GetMapping("/user")
	public RestObject user() {
		HubUser user = BugUtils.getAuthenticationUser();
		return RestObject.builder().data(user).build();
	}
}
