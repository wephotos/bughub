package com.github.wephotos.bughub.service;

import com.github.wephotos.bughub.entity.*;
import com.github.wephotos.bughub.entity.dto.BugDto;
import com.github.wephotos.bughub.entity.query.BugQuery;
import com.github.wephotos.bughub.entity.vo.BugVo;
import com.github.wephotos.bughub.mapper.BugMapper;
import com.github.wephotos.bughub.mapper.BugUserMapper;
import com.github.wephotos.bughub.mapper.ProjectMapper;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.Page;
import com.github.wephotos.bughub.utils.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Bug服务
 *
 * @author Dell-Aaron
 */
@Service("bugService")
@Transactional(rollbackFor = {Exception.class})
public class BugService {

    @Resource
    private BugMapper bugMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private BugUserMapper bugUserMapper;
    
    @Resource
    private ProjectUserRoleService projectUserRoleService;
    /**
     * 新增bug
     *
     * @param bug bug
     * @return id
     */
    public String add(BugDto bug) {
    	if(StringUtils.isBlank(bug.getId())) {
        	bug.setId(BugUtils.uuid());
        }
        Project project = projectMapper.selectByPrimaryKey(bug.getProjectId());
        bug.setProjectId(project.getId());
        bug.setProjectName(project.getName());
        bug.setCreateTime(BugUtils.timestamp());
        bugMapper.insert(bug);
        //创建人
        String userId = bug.getUserId();
        List<BugUser> users = bug.getUsers();
        boolean match = users.stream().anyMatch(p -> {
        	if(userId.equals(p.getUserId())) {
        		p.setCreator(true);
        		return true;
        	}else {
        		return false;
        	}
        });
        //创建者不是归属人添加进BUG用户中
        if(!match) {
	        BugUser bugUser = new BugUser();
	        bugUser.setUserId(bug.getUserId());
	        bugUser.setUserName(bug.getUserName());
	        bugUser.setCreator(true);
	        bugUser.setCreateTime(BugUtils.timestamp());
	        users.add(bugUser);
        }
        insertBugUser(bug, users);
        return bug.getId();
    }

    /**
     * 插入BugUser
     * @param bug
     * @param users
     */
    protected void insertBugUser(final Bug bug, final List<BugUser> users) {
        new HashSet<>(users).stream().map(user -> {
                	user.setId(BugUtils.uuid());
                	user.setBugId(bug.getId());
                	user.setFromUserId(bug.getUserId());
                	user.setFromUserName(bug.getUserName());
                	user.setCreateTime(BugUtils.timestamp());
                    return user;
                }).forEach(bugUserMapper::insert);
    }


    /**
     * 修改bug
     *
     * @param bug bug
     * @return bug id
     */
    public boolean update(Bug bug) {
        bug.setCreateTime(BugUtils.timestamp());
        return bugMapper.updateByPrimaryKeySelective(bug) == 1;
    }

    /**
     * 删除bug
     *
     * @param id id
     * @return boolean
     */
    public boolean delete(String id) {
        //删除bug
        boolean b = bugMapper.deleteByPrimaryKey(id) == 1;
        //删除附件
        //删除bug对应的人员
        boolean bu = bugUserMapper.deleteByBugId(id) == 1;
        return  b && bu;
    }

    /**
     * 根据id查询bug
     *
     * @param id id
     * @return bug
     */
    public Bug selectByPrimaryKey(String id) {
        return bugMapper.selectByPrimaryKey(id);
    }
    
    /**
     * 获取Bug,包含用户信息
     * @param id Bug标识
     * @return
     */
    public BugVo findBugVo(String id) {
        BugVo bugVo = bugMapper.selectByPrimaryKeyForVo(id);
        List<BugUser> users = bugUserMapper.listBugUser(id);
        bugVo.setUsers(users);
        return bugVo;
    }

    /**
     * bug分页列表
     *
     * @param pageable {@link Pageable}
     * @return Page
     */
    public Page<BugVo> pageList(Pageable pageable) {
    	//查询用户在项目中的角色
    	String userId = pageable.getUserId();
    	String projectId = ((BugQuery)pageable.getCondition()).getProjectId();
    	Project project = projectMapper.selectByPrimaryKey(projectId);
    	List<ProjectUserRole> list = projectUserRoleService.list(projectId, userId);
    	if(list.isEmpty() && !project.getUserId().equals(userId)) {
    		throw new IllegalArgumentException("你无权访问此项目");
    	}
    	//匹配管理员或项目经理
    	boolean match = list.stream().anyMatch(p -> HubUserRole.PM.name().equals(p.getRole()));
    	pageable.setStart((pageable.getCurr() - 1) * pageable.getLimit());
    	Page<BugVo> page = new Page<>();
    	//项目经理或创建者
    	if(match || project.getUserId().equals(userId)) {
    		page.setData(bugMapper.pageManagerList(pageable));
            page.setCount(bugMapper.pageManagerCount(pageable));
    	}else {
    		page.setData(bugMapper.pageList(pageable));
    		page.setCount(bugMapper.pageCount(pageable));
    	}
        return page;
    }
    
    /**
     * 项目BUG统计数据
     * @param projectId 项目ID
     * @param userId 用户标识
     * @return Map
     */
    public Map<BugState, List<Integer>> statistics(String projectId, String userId){
    	List<BugStats> results = bugMapper.statistics(projectId, userId);
    	Map<BugState, List<Integer>> data = new EnumMap<>(BugState.class);
    	for(BugState state : BugState.values()) {
    		BugLevel[] levels = BugLevel.values();
    		List<Integer> value = new ArrayList<>(levels.length);
    		for(BugLevel level : levels) {
				Optional<Integer> first = results.stream()
						.filter(p -> state.getValue().equals(p.getState()) && level.getLevel().equals(p.getLevel()))
						.map(BugStats::getCount)
						.findFirst();
				value.add(first.orElse(0));
			}
    		data.put(state, value);
    	}
    	return data;
    }

    /**
     * 更新bug的state
     * @param state state
     * @param bugId bugId
     * @return boolean
     */
    public boolean updateState(Integer state, String bugId) {
        return bugMapper.updateState(state, bugId) == 1;
    }
}
