package com.github.wephotos.bughub.controller;

import com.github.wephotos.bughub.entity.BugState;
import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.dto.BugDto;
import com.github.wephotos.bughub.entity.query.BugQuery;
import com.github.wephotos.bughub.entity.vo.BugVo;
import com.github.wephotos.bughub.service.BugService;
import com.github.wephotos.bughub.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Bug管理web接口
 *
 * @author xcloading
 */
@RestController
@RequestMapping("/bug")
public class BugController {

    @Resource
    private BugService bugService;

    /**
     * 新增bug
     *
     * @param bug bug
     * @return RestObject
     */
    @PostMapping("/add")
    public RestObject add(@RequestBody BugDto bug) {
    	HubUser user = BugUtils.getAuthenticationUser();
        bug.setUserId(user.getId());
        bug.setUserName(user.getName());
        String id = bugService.add(bug);
        return RestObject.builder().data(id).build();
    }

    /**
     * 删除bug
     *
     * @param id id
     * @return RestObject
     */
    @GetMapping("delete/{id}")
    public RestObject deleteById(@PathVariable String id) {
        boolean delete = bugService.delete(id);
        return RestObject.builder().data(delete).build();
    }

    /**
     * 根据id获取bug
     *
     * @param id id
     * @return bug
     */
    @GetMapping("get/{id}")
    public RestObject get(@PathVariable String id) {
        BugVo bug = bugService.findBugVo(id);
        return RestObject.builder().data(bug).build();
    }

    /**
     * bug 分页列表
     *
     * @return RestObject
     */
    @PostMapping("pageList")
    public RestPage pageList(BugQuery query, Pageable pageable) {
        HubUser user = BugUtils.getAuthenticationUser();
        if (StringUtils.isBlank(pageable.getUserId())) {
        	pageable.setUserId(user.getId());
        }
        pageable.setCondition(query);
        Page<BugVo> page = bugService.pageList(pageable);
        return RestPage.builder().data(page.getData()).count(page.getCount()).build();
    }

    /**
     * 统计项目Bug
     * @param projectId
     * @param userId
     * @return
     */
    @GetMapping("/stats/{projectId}")
    public RestObject statistics(@PathVariable String projectId, String userId) {
    	Map<BugState, List<Integer>> result = bugService.statistics(projectId, userId);
    	return RestObject.builder().data(result).build();
    }
}
