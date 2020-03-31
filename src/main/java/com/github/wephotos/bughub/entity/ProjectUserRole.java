package com.github.wephotos.bughub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 项目用户角色
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
public class ProjectUserRole extends ProjectUser {
	
    /**
     * 担任的角色
     */
    private String role;
}
