package com.github.wephotos.bughub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
 * 项目实体
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@ToString
public class Project {
	/**
	 * 主键
	 */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 创建人
     */
    private String userId;
    /**
     * 创建人
     */
    private String userName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 项目用户(包含角色)
     */
    private List<ProjectUserRole> users = Collections.emptyList();
    /**
     * BUG统计
     */
    private List<BugStats> stats = Collections.emptyList();
}