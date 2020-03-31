package com.github.wephotos.bughub.utils;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.github.wephotos.bughub.entity.HubFile;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Bucket.DeleteObjectOutput;
import com.qingstor.sdk.service.Bucket.GetObjectOutput;
import com.qingstor.sdk.service.Bucket.PutObjectInput;
import com.qingstor.sdk.service.Bucket.PutObjectOutput;

import lombok.extern.slf4j.Slf4j;

import com.qingstor.sdk.service.QingStor;

/**
 * 青云存储
 * @author TQ
 *
 */
@Slf4j
@Primary
@Component("fileStorQingCloud")
public class FileStorQingCloud implements FileStor {
	/**
	 *  存储区域
	 */
	@Value("${qy.zone.name}")
	private String zone;
	/**
	 * 存储空间
	 */
	@Value("${qy.bucket.name}")
	private String bucketName;
	/**
	 * 密钥ID
	 */
	@Value("${qy.access.key.id}")
	private String accessKey;
	/**
	 * 密钥KEY
	 */
	@Value("${qy.secret.access.key}")
	private String accessSecret;
	/**
	 * 存储空间
	 */
	private Bucket bucket;
	
	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void initConfig() {
		EnvContext evn = new EnvContext(accessKey, accessSecret);
		QingStor stor = new QingStor(evn, zone);
		bucket = stor.getBucket(bucketName, zone);
	}
	
	@Override
	public String getNewObjectName(String filename) {
		filename = filename.replaceAll("[^a-zA-Z0-9_\u4E00-\u9FA5&&[^.*-]]+", "");
		LocalDate now = LocalDate.now();
		return String.format("bughub/%s/%s/%s/%s-%s", now.getYear(), 
				now.getMonthValue(), now.getDayOfMonth(),
				System.currentTimeMillis(), filename);
	}
	
	@Override
	public void storage(HubFile file) throws IOException {
		PutObjectInput input = new PutObjectInput();
		input.setContentLength(file.getSize());
		input.setBodyInputStream(file.getInputStream());
		PutObjectOutput output;
		try {
			output = bucket.putObject(file.getObjectName(), input);
		} catch (QSException e) {
			throw new FileStorException("上传文件流到青云失败", e);
		}
		if(output.getStatueCode() != HttpServletResponse.SC_CREATED) {
			throw new FileStorException(String.format("上传附件到青云失败:%s", output.getMessage()));
		}
		log.info("附件上传青云成功:{}", file.getObjectName());
	}

	@Override
	public void delete(String objectName) throws IOException {
		DeleteObjectOutput output;
		try {
			output = bucket.deleteObject(objectName);
		} catch (QSException e) {
			throw new FileStorException("删除青云附件失败", e);
		}
		if(output.getStatueCode() != HttpServletResponse.SC_NO_CONTENT) {
			throw new FileStorException(String.format("删除青云附件失败:%s", output.getMessage()));
		}
		log.info("删除青云附件成功:{}", objectName);
	}

	@Override
	public InputStream get(String objectName) throws IOException {
		GetObjectOutput output;
		try {
			output = bucket.getObject(objectName, null);
		} catch (QSException e) {
			throw new FileStorException("获取青云附件失败", e);
		}
		log.info("获取青云附件成功:{}", objectName);
		return output.getBodyInputStream();
	}

}
