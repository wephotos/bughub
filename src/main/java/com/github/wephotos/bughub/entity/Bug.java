package com.github.wephotos.bughub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Bug实体
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@ToString
public class Bug {
	/**
	 * 主键
	 */
    private String id;
    /**
	 * 状态
	 */
    private Integer state;
    /**
	 * 级别
	 */
    private Integer level;
    /**
	 * 标题
	 */
    private String title;
    /**
	 * 描述
	 */
    private String description;
    /**
	 * 创建人
	 */
    private String userId;
    /**
	 * 创建人
	 */
    private String userName;
    /**
	 * 项目标识
	 */
    private String projectId;
    /**
	 * 项目名
	 */
    private String projectName;
    /**
	 * 项目版本
	 */
    private String projectVersion;
    /**
	 * 创建时间
	 */
    private Date createTime;
}