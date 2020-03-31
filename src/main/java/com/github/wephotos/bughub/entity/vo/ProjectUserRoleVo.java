package com.github.wephotos.bughub.entity.vo;

import com.github.wephotos.bughub.entity.ProjectUserRole;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 项目用户角色ViewData
 * @author TQ
 *
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ProjectUserRoleVo extends ProjectUserRole {

	/**
	 * 用户名
	 */
	private String userName;
}
