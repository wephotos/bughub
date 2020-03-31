package com.github.wephotos.bughub.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.wephotos.bughub.entity.HubFile;
import com.github.wephotos.bughub.entity.vo.UploadVo;
import com.github.wephotos.bughub.mapper.HubFileMapper;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.FileStor;

/**
 * 附件管理服务
 * @author Dell-Aaron
 *
 */
@Service("hubFileService")
@Transactional(rollbackFor = {Exception.class})
public class HubFileService {

	/**
	 * 存储服务
	 */
	@Resource
	private FileStor stor;
	
	@Resource
	private HubFileMapper hubFileMapper;
	
	public void setStor(FileStor stor) {
		this.stor = stor;
	}
	
	public FileStor getStor() {
		return stor;
	}
	
	/**
	 * 上传
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public UploadVo upload(HubFile file) throws IOException {
		UploadVo uploadVo = new UploadVo();
		if(StringUtils.isBlank(file.getOwner())){
			file.setOwner(BugUtils.uuid());
		}
		file.setObjectName(stor.getNewObjectName(file.getName()));
		stor.storage(file);
		file.setId(BugUtils.uuid());
		file.setCreateTime(BugUtils.timestamp());
		uploadVo.setId(file.getId());
		uploadVo.setName(file.getName());
		uploadVo.setFormId(file.getOwner());
		hubFileMapper.insert(file);
		return uploadVo;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws IOException 
	 */
    public int deleteByPrimaryKey(String id) throws IOException {
    	HubFile file = hubFileMapper.selectByPrimaryKey(id);
    	stor.delete(file.getObjectName());
    	return hubFileMapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 获取文件，包含文件流
     * @param id
     * @return
     * @throws IOException 
     */
    public HubFile getFile(String id) throws IOException {
    	HubFile file = hubFileMapper.selectByPrimaryKey(id);
    	file.setInputStream(stor.get(file.getObjectName()));
    	return file;
    }
    
    /**
     * 获取文件
     * @param owner
     * @return
     */
    public List<HubFile> list(String owner){
    	return hubFileMapper.list(owner);
    }

}
