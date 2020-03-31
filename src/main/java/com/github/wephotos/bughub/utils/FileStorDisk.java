package com.github.wephotos.bughub.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.github.wephotos.bughub.entity.HubFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 磁盘存储
 * @author TQ
 *
 */
@Slf4j
@Component("fileStor")
public class FileStorDisk implements FileStor {
	
	/**
	 * 文件存储默认目录
	 */
	public String directory = "./bugfiles";
	/**
	 * 获取文件存储目录
	 * @return
	 */
	public String getDirectory() {
		return directory;
	}
	/**
	 * 设置文件目录
	 * @param directory
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	@Override
	public String getNewObjectName(String filename) {
		LocalDate now = LocalDate.now();
		return String.format("%s/%s/%s/%s-%s", now.getYear(), 
				now.getMonthValue(), now.getDayOfMonth(),
				System.currentTimeMillis(), filename);
	}
	
	@Override
	public void storage(HubFile file) throws IOException {
		String objectName = file.getObjectName();
		File dest = new File(directory, objectName);
		log.info("destFile:{}", dest.getCanonicalPath());
		try(InputStream input = file.getInputStream()){
			try(OutputStream output = FileUtils.openOutputStream(dest)){
				IOUtils.copy(input, output);
			}
		}
	}
	
	@Override
	public void delete(String objectName) throws IOException {
		File file = new File(directory, objectName);
		log.info("delete:{}", file.getCanonicalPath());
		FileUtils.deleteQuietly(file);
	}

	@Override
	public InputStream get(String objectName) throws IOException {
		File file = new File(directory, objectName);
		log.info("get:{}", file.getCanonicalPath());
		return FileUtils.openInputStream(file);
	}

}
