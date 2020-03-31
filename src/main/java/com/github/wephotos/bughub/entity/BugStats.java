package com.github.wephotos.bughub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Bug统计结果
 * @author TQ
 *
 */
@Getter
@Setter
@ToString
public class BugStats {

	//状态
	@JsonIgnore
	private Integer state;
	//级别
	@JsonIgnore
	private Integer level;
	//项目ID
	@JsonIgnore
	private String projectId;
	//数量
	private int count;
	
	/**
	 * 获取Bug等级枚举
	 * @return
	 */
	public BugLevel getBugLevel() {
		return BugLevel.resolve(level);
	}
}
