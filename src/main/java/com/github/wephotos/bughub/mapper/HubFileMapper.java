package com.github.wephotos.bughub.mapper;

import com.github.wephotos.bughub.entity.HubFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 附件持久层接口
 * @author Dell-Aaron
 *
 */
@Mapper
public interface HubFileMapper extends BaseMapper<HubFile> {

	/**
	 * 获取所属附件
	 * @param owner 附件主体
	 * @return 附件集合
	 */
	List<HubFile> list(String owner);
	
}