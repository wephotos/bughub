package com.github.wephotos.bughub.entity;

/**
 * Bug级别枚举
 *
 * @author Dell-Aaron
 */
public enum BugLevel {

    BLOCKER("错误", 1),
    CRITICAL("严重", 2),
    NORMAL("一般", 3),
    ENHANCEMENT("建议", 4);

    private BugLevel(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public static BugLevel resolve(int value) {
        for (BugLevel state : BugLevel.values()) {
            if (state.getLevel() == value) {
                return state;
            }
        }
		return null;
    }

    /**
     * 级别名称
     */
    private String name;
    /**
     * 级别数值
     */
    private Integer level;

    public String getName() {
        return name;
    }

    public Integer getLevel() {
        return level;
    }
}
