package com.talentrecd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class SpringFileUpload {
	
	private static Logger log = LoggerFactory.getLogger(SpringFileUpload.class);
	
	public static String imageUpload(HttpServletRequest request,MultipartFile image){
		String filePath = "";
		try {
			String path = request.getSession().getServletContext().getRealPath("img");  
	        String fileName = new Date().getTime()+".jpg";  
	        File targetFile = new File(path, fileName);
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        } 
	        image.transferTo(targetFile);
	        filePath = request.getContextPath()+"/img/"+fileName;
		} catch (Exception e) {
			log.error("上传图片失败，原因：\n"+e.toString());
		}
		return filePath;
	}
	
	public static String fileUpload(HttpServletRequest request,MultipartFile file){
		String filePath = "";
		try {
			String path = request.getSession().getServletContext().getRealPath("file");  
	        String fileName = new Date().getTime()+".jpg";  
	        File targetFile = new File(path, fileName);
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        } 
	        file.transferTo(targetFile);
	        filePath = request.getContextPath()+"/file/"+fileName;
		} catch (Exception e) {
			log.error("上传图片失败，原因：\n"+e.toString());
		}
		return filePath;
	}
}
