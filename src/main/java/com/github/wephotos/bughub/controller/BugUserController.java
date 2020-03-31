package com.github.wephotos.bughub.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.bughub.entity.BugUser;
import com.github.wephotos.bughub.service.BugUserService;
import com.github.wephotos.bughub.utils.RestObject;

/**
 * 用户bug web接口
 *
 * @author xcloading
 */
@RestController
@RequestMapping("/bug-user")
public class BugUserController {
	
    @Resource
    private BugUserService bugUserService;

    /**
     * 新增用户bug
     *
     * @param bugUser bugUser
     * @return id
     */
    @PostMapping("/add")
    public RestObject add(BugUser bugUser) {
        bugUserService.insert(bugUser);
        return RestObject.builder().data(bugUser.getId()).build();
    }

    /**
     * 删除用户bug
     *
     * @param bugId Bug标识
     * @param userId 用户标识
     * @return RestObject
     */
    @GetMapping("/delete")
    public RestObject delete(String bugId, String userId) {
        boolean delete = bugUserService.delete(bugId, userId);
        return RestObject.builder().data(delete).build();
    }
    
    /**
     * 获取BUG用户
     * @param bugId
     * @return
     */
    @GetMapping("/list/{bugId}")
    public RestObject list(@PathVariable("bugId") String bugId) {
    	List<BugUser> data = bugUserService.listBugUser(bugId);
    	return RestObject.builder().data(data).build();
    }
}
