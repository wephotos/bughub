package com.github.wephotos.bughub.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.dto.ModifyPasswordDto;
import com.github.wephotos.bughub.service.HubUserService;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.Errors;
import com.github.wephotos.bughub.utils.Page;
import com.github.wephotos.bughub.utils.Pageable;
import com.github.wephotos.bughub.utils.RestObject;
import com.github.wephotos.bughub.utils.RestPage;

/**
 * 用户管理Web接口
 *
 * @author Dell-Aaron
 */
@RestController
@RequestMapping("/hub-user")
public class HubUserController {

    @Resource
    private HubUserService hubUserService;

    /**
     * 根据主键查询用户
     * @param id
     * @return
     */
    @RequestMapping("/get/{id}")
    public RestObject get(@PathVariable("id") String id) {
        HubUser user = hubUserService.selectByPrimaryKey(id);
        return RestObject.builder().data(user).build();
    }

    /**
     * 新增或修改用户
     *
     * @param user hubUser
     * @return RestObject
     */
    @PostMapping("/addOrUpdate")
    public RestObject addOrUpdate(HubUser user) {
        String id = hubUserService.addOrUpdate(user);
        return RestObject.builder().data(id).build();
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return
     */
    @GetMapping("/delete/{id}")
    public RestObject delete(@PathVariable("id") String id) {
    	boolean result = hubUserService.delete(id);
    	return RestObject.builder().data(result).build();
    }

	/**
	 * 用户分页
	 *
	 * @param pageable
	 * @return RestObject
	 */
	@PostMapping("/pageList")
	public RestPage pageList(Pageable pageable) {
		Page<HubUser> page = hubUserService.pageList(pageable);
		return RestPage.builder().data(page.getData()).count(page.getCount()).build();
	}

    /**
     * 修改当前用户密码
     * @param mpd mpd
     * @return RestObject
     */
    @PostMapping("/modifyPassword")
	public RestObject modifyPassword(ModifyPasswordDto mpd){
        // 后期加入加密方式判断
        if (!StringUtils.equals(mpd.getOldPassword(), BugUtils.getAuthenticationUser().getPassword())) {
            return new RestObject(Errors.OLD_PASSWORD_ERROR);
        }
        if (StringUtils.equals(mpd.getOldPassword(), mpd.getPassword())) {
            return new RestObject(Errors.PASSWORD_ERROR);
        }
        if (!StringUtils.equals(mpd.getConfirmPwd(), mpd.getPassword())) {
            return new RestObject(Errors.CONFIRM_PASSWORD_ERROR);
        }
        HubUser hubUser = new HubUser();
        hubUser.setId(BugUtils.getAuthenticationUser().getId());
        hubUser.setPassword(mpd.getPassword());
        boolean b = hubUserService.updatePassword(hubUser);
        return RestObject.builder().data(b).build();
    }

}
