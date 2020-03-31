package com.github.wephotos.bughub.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <b>描述:</b>
 *
 * <p>分页数据模板</p>
 *
 * @version 1.0
 * @author Aaron.tian
 * @Date 2019年5月5日下午2:57:59
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
	/**
	 * 数据总数
	 */
	private int count;
	/**
	 * 数据集合
	 */
	private List<T> data;
}
