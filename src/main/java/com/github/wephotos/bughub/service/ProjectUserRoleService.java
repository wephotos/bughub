package com.github.wephotos.bughub.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.wephotos.bughub.entity.HubUserRole;
import com.github.wephotos.bughub.entity.ProjectUser;
import com.github.wephotos.bughub.entity.ProjectUserRole;
import com.github.wephotos.bughub.entity.vo.ProjectUserRoleVo;
import com.github.wephotos.bughub.mapper.ProjectUserMapper;
import com.github.wephotos.bughub.mapper.ProjectUserRoleMapper;
import com.github.wephotos.bughub.utils.BugUtils;

/**
 * 项目服务
 *
 * @author xcloading
 */
@Service("projectUserRoleService")
@Transactional(rollbackFor = {Exception.class})
public class ProjectUserRoleService {
	
    @Resource
    private ProjectUserMapper projectUserMapper;

    @Resource
    private ProjectUserRoleMapper projectUserRoleMapper;
    
    /**
     * 关联用户与项目
     * 
     * @param projectUser projectUser
     * @return 项目id
     */
    public String insert(ProjectUserRole projectUser) {
    	projectUser.setId(BugUtils.uuid());
    	projectUserRoleMapper.insert(projectUser);
        return projectUser.getId();
    }
    
    /**
     * 删除关联
     *
     * @param projectId 项目id
     * @param userId 用户id
     * @return 成功 true
     */
    public boolean deleteUserFromProject(String projectId, String userId) {
        return projectUserMapper.deleteUserFromProject(projectId, userId) == 1;
    }

    /**
     * 全量覆盖更新，先删除以前的记录
     * @param projectId 项目标识
     * @param users 用户集合
     */
    protected void overrideUpdate(String projectId, List<ProjectUserRole> users) {
    	projectUserMapper.deleteByProjectId(projectId);
    	projectUserRoleMapper.deleteByProjectId(projectId);
    	users.forEach(user -> {
    		user.setId(BugUtils.uuid());
    		user.setProjectId(projectId);
    	});
		Set<ProjectUser> pus = users.stream().map(ProjectUser::new).collect(Collectors.toSet());
    	projectUserMapper.insertBatch(pus);
    	projectUserRoleMapper.insertBatch(users);
    }

    /**
     * 查找项目关联的用户
     *
     * @param projectId 项目标识
     * @return 项目集合
     */
    public List<ProjectUserRole> list(String projectId) {
        return projectUserRoleMapper.listByProjectId(projectId);
    }
    
    /**
     * 根据项目和角色查询用户
     * @param projectId 项目id
     * @param role 角色
     * @return 用户
     */
    public List<ProjectUserRoleVo> list(String projectId, HubUserRole role) {
        return projectUserRoleMapper.listProjectUser(projectId,role);
    }

    /**
     * 获取用户在项目中的角色
     * @param projectId
     * @param userId
     * @return
     */
	public List<ProjectUserRole> list(String projectId, String userId) {
		return projectUserRoleMapper.listUserRole(projectId, userId);
	}
}
