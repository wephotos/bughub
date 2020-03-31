package com.github.wephotos.bughub.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.service.HubUserService;

@Component
public class HubUserDetailsService implements UserDetailsService {

	@Resource
	private HubUserService hubUserService;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		HubUser user = hubUserService.selectByAccount(username);
		if(user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		String roles = user.getRoles();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(StringUtils.isNotBlank(roles)) {
			for(String role : roles.split(",")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}
		}
		return new User(username, user.getPassword(), authorities);
	}

}
