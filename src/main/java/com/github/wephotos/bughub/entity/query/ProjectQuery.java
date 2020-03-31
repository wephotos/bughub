package com.github.wephotos.bughub.entity.query;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

/**
 * 项目查询条件
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@ToString
public class ProjectQuery {

	/**
	 * 项目名
	 */
	private String name;
	/**
	 * 用户标识
	 */
	private String userId;
}
