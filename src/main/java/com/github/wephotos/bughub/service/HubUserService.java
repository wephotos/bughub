package com.github.wephotos.bughub.service;

import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.mapper.HubUserMapper;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.Page;
import com.github.wephotos.bughub.utils.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 系统用户服务
 *
 * @author Dell-Aaron
 */
@Service("hubUserService")
@Transactional(rollbackFor = {Exception.class})
public class HubUserService {

    @Resource
    private HubUserMapper hubUserMapper;

    /**
     * 保存用户
     * @param user
     * @return
     */
	public String addOrUpdate(HubUser user) {
		if(StringUtils.isBlank(user.getId())) {
			return insert(user);
		}else {
			return String.valueOf(update(user));
		}
	}

    /**
     * 新增用户
     *
     * @param user user
     * @return String
     */
    public String insert(HubUser user) {
    	HubUser temp = selectByAccount(user.getAccount());
    	if(temp != null) {
    		throw new IllegalArgumentException("用户账号已存在");
    	}
        user.setId(BugUtils.uuid());
        user.setCreateTime(BugUtils.timestamp());
        user.setUpdateTime(user.getCreateTime());
        hubUserMapper.insert(user);
        return user.getId();
    }

    /**
     * 删除用户
     *
     * @param id id
     * @return boolean
     */
    public boolean delete(String id) {
        return hubUserMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 更新用户
     *
     * @param user user
     * @return boolean
     */
    public boolean update(HubUser user) {
    	String account = user.getAccount();
    	if(StringUtils.isNotBlank(account)) {
    		//原用户信息
    		HubUser puser = selectByPrimaryKey(user.getId());
    		if(!account.equals(puser.getAccount())) {
    			HubUser auser = selectByAccount(puser.getAccount());
    			if(auser != null) {
    				throw new IllegalArgumentException("用户账号已存在");
    			}
    		}
    	}
        user.setUpdateTime(BugUtils.timestamp());
        return hubUserMapper.updateByPrimaryKeySelective(user) == 1;
    }

    /**
     * 根据主键查询
     *
     * @param id id
     * @return HubUser
     */
    public HubUser selectByPrimaryKey(String id) {
        return hubUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据账号获取用户
     *
     * @param account account
     * @return HubUser
     */
    public HubUser selectByAccount(String account) {
        return hubUserMapper.selectByAccount(account);
    }

	/**
	 * 用户分页
	 * @param pageable
	 * @return Page<HubUser>
	 */
    public Page<HubUser> pageList(Pageable pageable) {
    	pageable.setStart((pageable.getCurr() - 1) * pageable.getLimit());
        Page<HubUser> page = new Page<>();
        page.setCount(hubUserMapper.pageCount(pageable));
        page.setData(hubUserMapper.pageList(pageable));
        return page;
    }

    /**
     * 修改用户密码
     * @param hubUser hubUser
     * @return boolean
     */
    public boolean updatePassword(HubUser hubUser){
        return hubUserMapper.updatePassword(hubUser) == 1;
    }
}
