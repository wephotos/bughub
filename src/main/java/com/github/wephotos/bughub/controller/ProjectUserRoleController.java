package com.github.wephotos.bughub.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.bughub.entity.HubUserRole;
import com.github.wephotos.bughub.entity.ProjectUserRole;
import com.github.wephotos.bughub.entity.vo.ProjectUserRoleVo;
import com.github.wephotos.bughub.service.ProjectUserRoleService;
import com.github.wephotos.bughub.utils.RestObject;

/**
 * 项目人员web接口
 *
 * @author xcloading
 */
@RestController
@RequestMapping("/project-user")
public class ProjectUserRoleController {
	
    @Resource
    private ProjectUserRoleService projectUserRoleService;

    /**
     * 项目关联用户
     *
     * @param pur {@link ProjectUserRole}
     * @return RestObject
     */
    @PostMapping("/add")
    public RestObject add(ProjectUserRole pur) {
        projectUserRoleService.insert(pur);
        return RestObject.builder().data(pur.getId()).build();
    }

	/**
	 * 查询项目下的授权用户
	 * @param projectId
	 * @return
	 */
	@GetMapping("listByProjectId")
	public RestObject listByProjectId(String projectId) {
		List<ProjectUserRole> data = projectUserRoleService.list(projectId);
		return RestObject.builder().data(data).build();
	}
	
	/**
	 * 从项目删除授权用户
	 * @param projectId
	 * @param userId
	 * @return
	 */
	@GetMapping("deleteUserFromProject")
	public RestObject deleteUserFromProject(String projectId, String userId) {
		boolean res = projectUserRoleService.deleteUserFromProject(projectId, userId);
		return RestObject.builder().data(res).build();
	}

	/**
	 * 根据项目查询开发用户
	 * @param projectId 项目ID
	 * @return RestObject
	 */
	@GetMapping("/list-developer/{projectId}")
	public RestObject getUsersByProjectId(@PathVariable String projectId){
		List<ProjectUserRoleVo> list = projectUserRoleService.list(projectId, HubUserRole.DEVELOPER);
		return RestObject.builder().data(list).build();
	}
}
