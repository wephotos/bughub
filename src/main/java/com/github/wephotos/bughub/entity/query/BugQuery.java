package com.github.wephotos.bughub.entity.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Bug查询条件
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
public class BugQuery {

	/**
	 * 查询关键字
	 */
	private String word;
	/**
	 * 级别
	 */
	private Integer level;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 项目
	 */
	private String projectId;

}
