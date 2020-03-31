package com.github.wephotos.bughub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Bug轨迹追踪实体
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@ToString
public class BugTrack {
	/**
	 * 主键
	 */
    private String id;
    /**
	 * Bug标识
	 */
    private String bugId;
    /**
	 * Bug状态
	 */
    private Integer state;
    /**
	 * 标识
	 */
    private String title;
    /**
	 * 描述
	 */
    private String description;
    /**
	 * 版本
	 */
    private String version;
    /**
	 * 用户标识
	 */
    private String userId;
    /**
	 * 用户姓名
	 */
    private String userName;
    /**
	 * 创建时间
	 */
    private Date createTime;

}