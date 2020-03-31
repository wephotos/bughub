package com.github.wephotos.bughub.entity.vo;

import java.util.Collections;
import java.util.List;

import com.github.wephotos.bughub.entity.HubFile;
import com.github.wephotos.bughub.entity.ProjectVersion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 项目发布记录
 *
 * @author Dell-Aaron
 */
@Getter
@Setter
@ToString
public class ProjectVersionVo extends ProjectVersion {

	/**
	 * 版本附件
	 */
	private List<HubFile> files = Collections.emptyList();
}