package com.github.wephotos.bughub.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.wephotos.bughub.entity.HubUserRole;
import com.github.wephotos.bughub.entity.ProjectUserRole;
import com.github.wephotos.bughub.entity.vo.ProjectUserRoleVo;

/**
 * 项目用户角色数据层接口
 * @author TQ
 *
 */
@Mapper
public interface ProjectUserRoleMapper extends BaseMapper<ProjectUserRole> {

	/**
	 * 查找项目中的用户
	 * @param projectId
	 * @return
	 */
	List<ProjectUserRole> listByProjectId(String projectId);
	
	/**
	 * 从项目中移除用户角色
	 * @param pur 包含项目/用户/角色的对象
	 * @return 影响行数
	 */
	int deleteByPur(ProjectUserRole pur);

	/**
	 * 删除项目用户
	 * @param projectId 项目标识
	 * @return
	 */
	int deleteByProjectId(String projectId);
	/**
	 * 批量插入
	 * @param data
	 * @return
	 */
	int insertBatch(@Param("data") Collection<ProjectUserRole> data);

	/**
	 *根据项目和角色查询用户
	 * @param projectId 项目id
	 * @param role 角色名称
	 * @return 用户
	 */
	List<ProjectUserRoleVo> listProjectUser(@Param("projectId") String projectId, @Param("role") HubUserRole role);

	/**
	 * 获取用户在项目中的角色
	 * @param projectId
	 * @param userId
	 * @return
	 */
	List<ProjectUserRole> listUserRole(@Param("projectId") String projectId, @Param("userId") String userId);
}
