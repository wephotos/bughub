package com.github.wephotos.bughub.controller;

import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.ProjectVersion;
import com.github.wephotos.bughub.service.ProjectVersionService;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.RestObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目发布web接口
 *
 * @author xcloading
 */
@RestController
@RequestMapping("/project-version")
public class ProjectVersionController {
	
    @Resource
    private ProjectVersionService projectReleaseService;

    /**
     * 项目发布
     * @param release ProjectRelease
     * @return RestObject
     */
    @PostMapping("/add")
    public RestObject add(@RequestBody ProjectVersion release){
    	HubUser user = BugUtils.getAuthenticationUser();
        String id = projectReleaseService.insert(release, user);
        return RestObject.builder().data(id).build();
    }

    /**
     * 查询项目的版本发布记录
     * @param projectId
     * @return
     */
    @GetMapping("/list/{id}")
    public RestObject list(@PathVariable("id") String projectId){
        List<ProjectVersion> list = projectReleaseService.list(projectId);
        return RestObject.builder().data(list).build();
    }
}
