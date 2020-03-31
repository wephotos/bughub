package com.github.wephotos.bughub.mapper;

/**
 * CRUD接口
 * @author Dell-Aaron
 *
 * @param <T>
 */
public interface BaseMapper<T> {

	/**
	 * 新增
	 * @param record
	 * @return
	 */
	int insert(T record);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 查询
     * @param id
     * @return
     */
    T selectByPrimaryKey(String id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

}
