package com.github.wephotos.bughub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 系统用户
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@ToString
public class HubUser {
	/**
	 * 活动状态
	 */
	public static final int ACTIVE = 1;
	/**
	 * 禁用状态
	 */
	public static final int DISABLED = 0;
	/**
	 * 主键
	 */
    private String id;
    /**
	 * 姓名
	 */
    private String name;
    /**
	 * 账号
	 */
    private String account;
    /**
	 * 密码
	 */
    private String password;
    /**
	 * 角色以逗号[,]分隔
	 */
    private String roles;
    /**
     * 状态
     */
    private Integer state = ACTIVE;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 更新时间
	 */
    private Date updateTime;

    /**
     * 是否管理员
     * @return
     */
    public boolean isAdmin() {
    	return HubUserRole.ADMIN.name().equals(roles);
    }
}