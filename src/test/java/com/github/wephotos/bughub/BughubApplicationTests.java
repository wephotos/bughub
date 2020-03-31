package com.github.wephotos.bughub;

import javax.annotation.Resource;

import com.github.wephotos.bughub.entity.*;
import com.github.wephotos.bughub.service.ProjectService;
import com.github.wephotos.bughub.service.ProjectUserRoleService;
import com.github.wephotos.bughub.utils.BugUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.github.wephotos.bughub.service.HubUserService;

@SpringBootTest
class BughubApplicationTests {

    @Resource
    private HubUserService hubUserService;
    @Resource
    private ProjectUserRoleService projectUserService;
    @Resource
    private ProjectService projectService;

    @Test
    void contextLoads() {

    }

    @Rollback(false)
    @Test
    void hubUserInsert() {
        HubUser user = new HubUser();
        user.setName("开发");
        user.setAccount("developer");
        user.setPassword("developer");
        user.setRoles(HubUserRole.DEVELOPER.toString());
        hubUserService.insert(user);
        System.out.println(user.getId());
    }

    @Test
    void projectInsert() {
        Project project = new Project();
        project.setName("Bug管理系统");
        project.setDescription("这是一个bug管理系统");
        project.setState(1);
        HubUser hubUser = new HubUser();
        hubUser.setId("db26c05776f94dfda90c9b12c6c581d2");
        hubUser.setName("测试");
        projectService.insert(project, hubUser);

        project = new Project();
        project.setName("SQLCloud Web数据库管理工具");
        project.setDescription("Web数据库管理工具");
        project.setState(1);
        hubUser = new HubUser();
        hubUser.setId("db26c05776f94dfda90c9b12c6c581d2");
        hubUser.setName("测试");
        projectService.insert(project, hubUser);

        project = new Project();
        project.setName("企业微信SDK");
        project.setDescription("企业微信SDK");
        project.setState(1);
        hubUser = new HubUser();
        hubUser.setId("db26c05776f94dfda90c9b12c6c581d2");
        hubUser.setName("测试");
        projectService.insert(project, hubUser);
    }

    @Test
    void projectUserInsert(){
//        ProjectUser projectUser = new ProjectUser();
//        projectUser.setRole(HubUserRole.DEVELOPER.toString());
//        projectUser.setUserId("2f3af8e15cda47948f937d786aff150e");
//        projectUser.setProjectId("1d9a699165074207973908f657fc1910");
//        projectUserService.insert(projectUser);

//        projectUser = new ProjectUser();
//        projectUser.setRole(HubUserRole.DEVELOPER.toString());
//        projectUser.setUserId("db26c05776f94dfda90c9b12c6c581d2");
//        projectUser.setProjectId("232b03327d0140e78ad5e5399c9ac3e2");
//        projectUserService.insert(projectUser);
//
//        projectUser = new ProjectUser();
//        projectUser.setRole(HubUserRole.DEVELOPER.toString());
//        projectUser.setUserId("db26c05776f94dfda90c9b12c6c581d2");
//        projectUser.setProjectId("8d4b3f6cdbab43bfbe9fbe76a8b5e624");
//        projectUserService.insert(projectUser);
        System.out.println(BugUtils.uuid());
    }
}
