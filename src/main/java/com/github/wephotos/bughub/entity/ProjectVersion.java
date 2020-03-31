package com.github.wephotos.bughub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 项目发布记录实体
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@ToString
public class ProjectVersion {
	/**
	 * 主键
	 */
    private String id;
    /**
     * 版本
     */
    private String version;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 描述
     */
    private String description;
    /**
     * 发布人
     */
    private String userId;
    /**
     * 发布人
     */
    private String userName;
    /**
     * 发布时间
     */
    private Date createTime;

}