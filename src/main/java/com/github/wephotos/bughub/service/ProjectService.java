package com.github.wephotos.bughub.service;

import com.github.wephotos.bughub.entity.BugStats;
import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.HubUserRole;
import com.github.wephotos.bughub.entity.Project;
import com.github.wephotos.bughub.entity.ProjectUserRole;
import com.github.wephotos.bughub.entity.query.ProjectQuery;
import com.github.wephotos.bughub.mapper.BugMapper;
import com.github.wephotos.bughub.mapper.ProjectMapper;
import com.github.wephotos.bughub.utils.BugUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目服务
 *
 * @author xcloading
 */
@Service("projectService")
@Transactional(rollbackFor = {Exception.class})
public class ProjectService {
	
    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectUserRoleService projectUserRoleService;
    
    @Resource
    private BugMapper bugMapper;
    
    /**
     * 新增项目
     *
     * @param project 项目
     * @param user 用户
     * @return boolean
     */
    public String insert(Project project, HubUser user) {
    	project.setId(BugUtils.uuid());
    	project.setUserId(user.getId());
    	project.setUserName(user.getName());
        project.setCreateTime(BugUtils.timestamp());
        project.setUpdateTime(project.getCreateTime());
        projectMapper.insert(project);
        return project.getId();
    }

    /**
     * 修改项目
     *
     * @param project 项目
     * @param user 用户
     * @return 项目id
     */
    public boolean update(Project project, HubUser user) {
        project.setUpdateTime(BugUtils.timestamp());
        return projectMapper.updateByPrimaryKeySelective(project) == 1;
    }

    /**
     * 保存项目
     * @param project
     * @param user
     * @return
     */
	public String addOrUpdate(Project project, HubUser user) {
		String result;
		if(StringUtils.isBlank(project.getId())) {
			result = insert(project, user);
		}else {
			result = String.valueOf(update(project, user));
		}
        //保存用户
        List<ProjectUserRole> users = project.getUsers();
        //添加创建人
        ProjectUserRole creator = new ProjectUserRole();
        creator.setUserId(user.getId());
        creator.setProjectId(project.getId());
        creator.setRole(HubUserRole.ADMIN.name());
        if(users == null || users.isEmpty()) {
        	users = new ArrayList<>();
        }
        users.add(creator);
        projectUserRoleService.overrideUpdate(project.getId(), users);
        
        return result;
	}
	
    /**
     * 删除项目
     *
     * @param id 项目id
     * @return 成功 true
     */
    public boolean delete(String id) {
        return projectMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 根据id查询项目
     *
     * @param id 项目id
     * @return 项目
     */
    public Project selectByPrimaryKey(String id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        List<ProjectUserRole> users = projectUserRoleService.list(id);
        project.setUsers(users);
        return project;
    }

    /**
     * 根据用户查找项目列表
     *
     * @param query 
     * @return 项目集合
     */
    public List<Project> listQuery(ProjectQuery query) {
    	//查询项目
    	List<Project> projects;
    	if(StringUtils.isBlank(query.getUserId())) {
    		projects = projectMapper.listAll();
    	}else {
    		projects = projectMapper.listQuery(query);
    	}
        //统计项目BUG
        List<BugStats> stats = bugMapper.statsNotClosed(query.getUserId());
        Map<String, List<BugStats>> map = stats.stream()
        		.collect(Collectors.groupingBy(BugStats::getProjectId));
        projects.forEach(p -> p.setStats(map.getOrDefault(p.getId(), Collections.emptyList())));
        return projects;
    }

}
