package com.github.wephotos.bughub.mapper;


import com.github.wephotos.bughub.entity.Project;
import com.github.wephotos.bughub.entity.query.ProjectQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 项目持久层接口
 *
 * @author Dell-Aaron
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
	
	/**
	 * 查询所有项目
	 * @return
	 */
	List<Project> listAll();
	
	/**
	 * 项目列表查询
	 * @param query
	 * @return
	 */
	List<Project> listQuery(ProjectQuery query);

	
}