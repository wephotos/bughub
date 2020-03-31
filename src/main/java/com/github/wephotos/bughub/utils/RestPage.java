package com.github.wephotos.bughub.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 接口返回的数据结构
 * @author Dell-Aaron
 *
 */
@Getter
@Setter
@SuperBuilder
@ToString(callSuper=true)
public class RestPage extends RestObject{

	/**
	 * 查询总数
	 */
	private int count;

}
