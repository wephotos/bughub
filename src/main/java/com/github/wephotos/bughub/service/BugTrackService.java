package com.github.wephotos.bughub.service;

import com.github.wephotos.bughub.entity.Bug;
import com.github.wephotos.bughub.entity.BugState;
import com.github.wephotos.bughub.entity.BugTrack;
import com.github.wephotos.bughub.entity.BugUser;
import com.github.wephotos.bughub.entity.HubFile;
import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.vo.BugTrackVo;
import com.github.wephotos.bughub.mapper.BugTrackMapper;
import com.github.wephotos.bughub.utils.BugUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Bug追踪服务
 * @author Dell-Aaron
 *
 */
@Service("bugTrackService")
@Transactional(rollbackFor = {Exception.class})
public class BugTrackService {

	@Resource
	private BugTrackMapper bugTrackMapper;
	@Resource
	private BugService bugService;
	@Resource
	private HubFileService hubFileService;
	@Resource
	private BugUserService bugUserService;
	/**
	 * 添加BUG追踪轨迹
	 * @param bugTrack
	 * @param hubUser
	 * @return
	 */
	public String add(BugTrack bugTrack, HubUser hubUser) {
		if(StringUtils.isBlank(bugTrack.getId())) {
			bugTrack.setId(BugUtils.uuid());
		}
		bugTrack.setUserId(hubUser.getId());
		bugTrack.setUserName(hubUser.getName());
		bugTrack.setCreateTime(BugUtils.timestamp());
		bugTrackMapper.insert(bugTrack);
		//更新bug表的state
		bugService.updateState(bugTrack.getState(), bugTrack.getBugId());
		return bugTrack.getId();
	}

	/**
	 * BUG追踪列表
	 * @param bugId bug标识
	 * @return List<BugTrack>
	 */
	public List<BugTrackVo> list(String bugId) {
		List<BugTrackVo> list = bugTrackMapper.list(bugId);
		//轨迹附件
		list.forEach(track ->{
			List<HubFile> files = hubFileService.list(track.getId());
			track.setFiles(files);
		});
		Bug bug = bugService.selectByPrimaryKey(bugId);
		BugTrackVo track = new BugTrackVo();
		track.setCreateTime(bug.getCreateTime());
		track.setUserName(bug.getUserName());
		track.setUserId(bug.getUserId());
		track.setState(BugState.OPEN.getValue());
		track.setTitle(bug.getTitle());
		track.setVersion(bug.getProjectVersion());
		track.setDescription(bug.getDescription());
		track.setId(bug.getId());
		// bug附件
		List<HubFile> files = hubFileService.list(bugId);
		track.setFiles(files);
		list.add(0, track);
		return list;
	}

	/**
	 * 移交BUG
	 * @param track
	 * @param hubUser
	 * @return
	 */
	public String handOver(BugTrack track, HubUser hubUser) {
		//移交BUG
		BugUser bugUser = new BugUser();
		bugUser.setBugId(track.getBugId());
		bugUser.setUserId(track.getUserId());
		bugUser.setUserName(track.getUserName());
		bugUserService.handOver(bugUser, hubUser);
		//添加追踪记录
		add(track, hubUser);
		return track.getId();
	}

}
