package com.github.wephotos.bughub.entity;

/**
 * 角色枚举
 * @author Dell-Aaron
 *
 */
public enum HubUserRole {

	ADMIN("管理员"),
	PM("项目经理"),
	DEVELOPER("开发"),
	TESTER("测试");
	
	private HubUserRole(String name) {
		this.name = name;
	}
	/**
	 * 角色名
	 */
	private String name;
	
	public String getName() {
		return name;
	}
}
