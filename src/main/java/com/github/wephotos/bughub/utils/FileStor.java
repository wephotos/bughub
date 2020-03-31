package com.github.wephotos.bughub.utils;

import java.io.IOException;
import java.io.InputStream;

import com.github.wephotos.bughub.entity.HubFile;

/**
 * 文件存储接口
 * @author TQ
 *
 */
public interface FileStor {
	
	/**
	 * 生成新的存储对象名称
	 * @param filename 原始文件名
	 * @return 存储对象名
	 */
	String getNewObjectName(String filename);
	/**
	 * 存储文件
	 * @param file 文件对象
	 * @throws IOException
	 */
	void storage(HubFile file) throws IOException;
	
	/**
	 * 删除文件
	 * @param objectName 文件对象名
	 * @throws IOException
	 */
	void delete(String objectName) throws IOException;
	
	/**
	 * 获取文件流
	 * @param objectName 文件对象名
	 * @return
	 * @throws IOException
	 */
	InputStream get(String objectName) throws IOException;

}
