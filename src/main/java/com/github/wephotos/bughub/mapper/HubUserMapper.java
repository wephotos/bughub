package com.github.wephotos.bughub.mapper;

import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.utils.Pageable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户持久层接口
 * @author Dell-Aaron
 *
 */
@Mapper
public interface HubUserMapper extends BaseMapper<HubUser> {

	/**
	 * 根据账号查询用户
	 * @param account 账号
	 * @return
	 */
	HubUser selectByAccount(String account);

	/**
	 * 用户分页
	 * @param pageable pageable
	 * @return List<HubUser>
	 */
    List<HubUser> pageList(Pageable pageable);

	/**
	 * 获取总记录数
	 * @param pageable pageable
	 * @return Integer
	 */
	Integer pageCount(Pageable pageable);

	/**
	 * 修改用户密码
	 * @param hubUser hubUser
	 * @return int
	 */
	int updatePassword(HubUser hubUser);
}