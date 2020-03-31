package com.github.wephotos.bughub.entity.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户数据
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@ToString
public class HubUserVo {
	//标识
	private String id;
	//姓名
	private String name;
	//账号
	private String account;
	//角色
	private String roles;
}
