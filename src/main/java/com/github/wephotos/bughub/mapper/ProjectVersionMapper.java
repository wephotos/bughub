package com.github.wephotos.bughub.mapper;

import com.github.wephotos.bughub.entity.ProjectVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目发布记录持久层接口
 * @author Dell-Aaron
 *
 */
@Mapper
public interface ProjectVersionMapper extends BaseMapper<ProjectVersion> {

	/**
	 * 查询项目版本记录
	 * @param projectId
	 * @return
	 */
    List<ProjectVersion> list(@Param("projectId") String projectId);
}