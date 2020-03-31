package com.github.wephotos.bughub.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.Project;
import com.github.wephotos.bughub.entity.query.ProjectQuery;
import com.github.wephotos.bughub.service.ProjectService;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.RestObject;

/**
 * 项目管理
 *
 * @author xcloading
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;

    /**
     * 保存项目
     *
     * @param project 项目
     * @return 项目id
     */
    @PostMapping("/addOrUpdate")
    public RestObject addOrUpdate(@RequestBody Project project) {
    	HubUser user = BugUtils.getAuthenticationUser();
        String id = projectService.addOrUpdate(project, user);
        return RestObject.builder().data(id).build();
    }

    /**
     * 根据id删除项目
     *
     * @param id id
     * @return 删除的条目
     */
    @PostMapping("/delete/{id}")
    public RestObject delete(@PathVariable String id) {
        boolean delete = projectService.delete(id);
        return RestObject.builder().data(delete).build();
    }

    /**
     * 根据id查找项目
     *
     * @param id id
     * @return 项目
     */
    @GetMapping("/get/{id}")
    public RestObject get(@PathVariable String id) {
        Project project = projectService.selectByPrimaryKey(id);
        return RestObject.builder().data(project).build();
    }

    /**
     * 根据操作人查项目列表
     *
     * @param query 查询条件
     * @return 所有符合条件项目
     */
    @GetMapping("/listQuery")
    public RestObject listQuery(ProjectQuery query) {
    	HubUser user = BugUtils.getAuthenticationUser();
    	//非管理员根据用户查询项目
    	if(!user.isAdmin()) {
    		query.setUserId(user.getId());
    	}
        List<Project> projects = projectService.listQuery(query);
        return RestObject.builder().data(projects).build();
    }

}
