package com.github.wephotos.bughub.service;

import com.github.wephotos.bughub.entity.BugUser;
import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.mapper.BugUserMapper;
import com.github.wephotos.bughub.utils.BugUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

/**
 * BugUser服务
 *
 * @author xcloading
 */
@Service("bugUserService")
@Transactional(rollbackFor = {Exception.class})
public class BugUserService {

    @Resource
    private BugUserMapper bugUserMapper;

    /**
     * 新增bugUser
     *
     * @param bugUser bug
     * @return boolean
     */
    public String insert(BugUser bugUser) {
    	bugUser.setId(BugUtils.uuid());
    	bugUser.setCreateTime(BugUtils.timestamp());
        bugUserMapper.insert(bugUser);
        return bugUser.getId();
    }

    /**
     * 删除用户Bug
     *
     * @param bugId 
     * @param userId 
     * @return boolean
     */
    public boolean delete(String bugId, String userId) {
        return bugUserMapper.deleteUserFromBug(bugId, userId) == 1;
    }

    /**
     * 移交Bug
     * @param buser
     * @param user
     * @return
     */
	public void handOver(BugUser buser, HubUser user) {
		//删除当前用户
		delete(buser.getBugId(), user.getId());
		List<BugUser> users = listBugUser(buser.getBugId());
		//如果用户不存在则添加
		boolean exists = users.stream().anyMatch(p -> p.getUserId().equals(buser.getUserId()));
		if(!exists) {
			buser.setFromUserId(user.getId());
			buser.setFromUserName(user.getName());
			insert(buser);
		}
	}

	/**
	 * 获取BUG用户
	 * @param bugId BUG标识
	 * @return
	 */
	public List<BugUser> listBugUser(String bugId) {
		return bugUserMapper.listBugUser(bugId);
	}

}
