package com.github.wephotos.bughub.entity;

import com.github.wephotos.bughub.utils.BugUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 项目用户实体类
 *
 * @author Dell-Aaron
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"userId", "projectId"})
public class ProjectUser {
	/**
	 * 构造项目用户
	 * @param pur
	 */
	public ProjectUser(ProjectUserRole pur) {
		this.id = BugUtils.uuid();
		this.userId = pur.getUserId();
		this.projectId = pur.getProjectId();
	}
    /**
     * 主键
     */
    private String id;
    /**
     * 用户标识
     */
    private String userId;
    /**
     * 项目标识
     */
    private String projectId;

}