package com.github.wephotos.bughub.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Bug关联用户实体
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"bugId", "userId"})
public class BugUser {
	/**
	 * 主键
	 */
    private String id;
    /**
	 * BUG标识
	 */
    private String bugId;
    /**
	 * 用户标识
	 */
    private String userId;
    /**
	 * 用户名
	 */
    private String userName;
    /**
	 * 来自用户
	 */
    private String fromUserId;
    /**
	 * 来自用户
	 */
    private String fromUserName;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
     * 是否创建者
     */
    private Boolean creator = false;

}