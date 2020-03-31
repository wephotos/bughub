package com.github.wephotos.bughub.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.wephotos.bughub.entity.HubFile;
import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.vo.UploadVo;
import com.github.wephotos.bughub.service.HubFileService;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.ImageUtils;
import com.github.wephotos.bughub.utils.RestObject;

/**
 * 文件接口
 * @author TQ
 *
 */
@RestController
@RequestMapping("/hub-file")
public class HubFileController {

	@Resource
	private HubFileService hubFileService;
	
	/**
	 * 上传文件
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/upload")
	public RestObject upload(@RequestParam("file") MultipartFile origin, HubFile file, HttpSession session) throws IOException {
		HubUser user = BugUtils.getAuthenticationUser();
		try(InputStream input = origin.getInputStream()) {
			String filename = origin.getOriginalFilename();
			file.setInputStream(input);
			file.setName(filename);
			file.setUserId(user.getId());
			file.setUserName(user.getName());
			file.setSize(origin.getSize());
			file.setContentType(origin.getContentType());
			UploadVo upload = hubFileService.upload(file);
			return RestObject.builder().data(upload).build();
		}
	}
	
	/**
	 * 上传base64编码图片
	 * @param owner 归属
	 * @param base64 图片BASE64编码
	 * @return {@link RestObject}
	 * @throws IOException 
	 */
	@PostMapping("/upload/base64-image")
	public RestObject uploadBase64Image(String owner, String base64) throws IOException {
		HubUser user = BugUtils.getAuthenticationUser();
		//判断是否存在类似 data:image/png;base64, 的前缀
		String suffix;
		String contentType;
		int comma = base64.indexOf(',');
		if(comma != -1) {
			int slash = base64.indexOf('/');
			int semicolon = base64.indexOf(';');
			suffix = base64.substring(slash + 1, semicolon);
			contentType = base64.substring(5, semicolon);
			base64 = base64.substring(comma + 1);
		}else {
			suffix = "png";
			contentType = "image/png";
		}
		byte[] buf = Base64Utils.decodeFromString(base64);
		HubFile file = new HubFile();
		file.setOwner(owner);
		file.setInputStream(new ByteArrayInputStream(buf));
		file.setName(BugUtils.uuid().concat(".").concat(suffix));
		file.setUserId(user.getId());
		file.setUserName(user.getName());
		file.setSize(buf.length);
		file.setContentType(contentType);
		UploadVo upload = hubFileService.upload(file);
		return RestObject.builder().data(upload).build();
	}
	
	/**
	 * 文件下载
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@GetMapping("/get/{id}")
	public void get(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HubFile file = hubFileService.getFile(id);
		response.reset();
		response.setCharacterEncoding("UTF-8");
		String userAgent = request.getHeader("User-Agent");
		String filename = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.name());
		if(userAgent != null && userAgent.toLowerCase().contains("firefox")) {
			response.addHeader("Content-Disposition", "attachment;filename*=UTF-8''" + filename);
		}else {
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		}
		response.addHeader("Content-Length", "" + file.getSize());
		response.setContentType(file.getContentType());
		try(InputStream input = file.getInputStream()){
			IOUtils.copy(input, response.getOutputStream());
		}
	}
	
	/**
	 * 获取缩略图
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@GetMapping("/thumb/get/{id}")
	public void getThumb(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HubFile file = hubFileService.getFile(id);
		response.reset();
		response.setCharacterEncoding("UTF-8");
		String userAgent = request.getHeader("User-Agent");
		String filename = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.name());
		if(userAgent != null && userAgent.toLowerCase().contains("firefox")) {
			response.addHeader("Content-Disposition", "attachment;filename*=UTF-8''" + filename);
		}else {
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		}
		response.setContentType(file.getContentType());
		try(InputStream input = file.getInputStream()){
			byte[] data = ImageUtils.compressionStream(input, 100, 100, 0.75F);
			response.addHeader("Content-Length", "" + data.length);
			IOUtils.write(data, response.getOutputStream());
		}
	}
	
	/**
	 * 删除附件
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("/delete/{id}")
	public RestObject delete(@PathVariable("id") String id) throws IOException {
		hubFileService.deleteByPrimaryKey(id);
		return RestObject.builder().build();
	}
	
	/**
	 * 获取所属附件
	 * @param owner
	 * @return
	 */
	@GetMapping("/list/{owner}")
	public RestObject list(@PathVariable("owner") String owner) {
		List<HubFile> list = hubFileService.list(owner);
		return RestObject.builder().data(list).build();
	}
}
