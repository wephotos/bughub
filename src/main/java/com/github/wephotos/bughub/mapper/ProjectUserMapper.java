package com.github.wephotos.bughub.mapper;

import com.github.wephotos.bughub.entity.ProjectUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * 项目关联用户持久层接口
 * @author Dell-Aaron
 *
 */
@Mapper
public interface ProjectUserMapper extends BaseMapper<ProjectUser> {

	/**
	 * 从项目中删除指定用户
	 * @param projectId
	 * @param userId
	 * @return
	 */
	int deleteUserFromProject(@Param("projectId")String projectId,@Param("userId") String userId);
	
	/**
	 * 删除项目用户
	 * @param projectId 项目标识
	 */
	int deleteByProjectId(String projectId);
	
	/**
	 * 批量插入
	 * @param data
	 * @return
	 */
	int insertBatch(@Param("data") Collection<ProjectUser> data);
}