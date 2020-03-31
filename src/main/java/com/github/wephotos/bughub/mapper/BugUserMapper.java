package com.github.wephotos.bughub.mapper;

import com.github.wephotos.bughub.entity.BugUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * BUG关联用户持久层接口
 * @author Dell-Aaron
 *
 */
@Mapper
public interface BugUserMapper extends BaseMapper<BugUser> {

	/**
	 * 删除用户Bug
	 * @param bugId
	 * @param userId
	 * @return
	 */
	int deleteUserFromBug(@Param("bugId") String bugId, @Param("userId") String userId);

	/**
	 * 删除BUG用户
	 * @param id
	 * @return
	 */
	int deleteByBugId(String id);

	/**
	 * 获取BUG用户
	 * @param bugId
	 * @return
	 */
    List<BugUser> listBugUser(String bugId);

}