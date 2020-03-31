package com.github.wephotos.bughub.utils;

import lombok.Data;

/**
 * <b>描述:</b>
 *
 * <p>分页条件</p>
 *
 * @version 1.0
 * @author Aaron.tian
 * @Date 2019年5月5日下午3:08:52
 * @since JDK1.8
 */
@Data
public class Pageable {
	/**
	 * 当前页码
	 */
	private int curr = 1;
	/**
	 * 起始下标
	 */
	private int start = 0;
	/**
	 * 每页条数
	 */
	private int limit = 15;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 查询条件
	 */
	private Object condition;
	/**
	 * 排序字段
	 */
	private String sortField;
	/**
	 * ASC DESC
	 */
	private String sortDirection;
}
