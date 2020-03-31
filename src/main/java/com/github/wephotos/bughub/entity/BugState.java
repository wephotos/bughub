package com.github.wephotos.bughub.entity;

/**
 * Bug状态枚举
 * @author Dell-Aaron
 *
 */
public enum BugState {
	
	OPEN("开启", 1),
	FIXED("解决", 2),
	REJECT("驳回", 3),
	CLOSED("关闭", 4);
	
	private BugState(String name, int value) {
		this.name = name;
		this.value = value;
	}
	/**
	 * 状态名
	 */
	private String name;
	/**
	 * 状态值
	 */
	private Integer value;
	
	public String getName() {
		return name;
	}
	
	public Integer getValue() {
		return value;
	}

	public static BugState resolve(int value) {
		for (BugState state : BugState.values()) {
			if (state.getValue() == value) {
				return state;
			}
		}
		return null;
	}
}
