package com.github.wephotos.bughub.service;

import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.ProjectVersion;
import com.github.wephotos.bughub.mapper.ProjectVersionMapper;
import com.github.wephotos.bughub.utils.BugUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 项目发布服务
 *
 * @author Dell-Aaron
 */
@Service("projectReleaseService")
@Transactional(rollbackFor = {Exception.class})
public class ProjectVersionService {
	
    @Resource
    private ProjectVersionMapper projectReleaseMapper;

    /**
     * 发布项目
     * @param release
     * @param user
     * @return
     */
    public String insert(ProjectVersion release, HubUser user) {
        if(StringUtils.isBlank(release.getId())){
            release.setId(BugUtils.uuid());
        }
        release.setCreateTime(BugUtils.timestamp());
        release.setUserId(user.getId());
        release.setUserName(user.getName());
        if(StringUtils.isBlank(release.getVersion())) {
        	String version = DateFormatUtils.format(new Date(), "yyyy.MM.dd.HH.mm");
        	release.setVersion(version);
        }
        projectReleaseMapper.insert(release);
        return release.getId();
    }

    /**
     * 查询项目版本记录
     * @param projectId
     * @return
     */
    public List<ProjectVersion> list(String projectId) {
        return projectReleaseMapper.list(projectId);
    }
}
