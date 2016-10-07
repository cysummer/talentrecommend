package com.talentrecd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	
	/* lq */
	
	private String productinfoImgUrl = SystemUtil.getAppConfig("productinfoImg");
	
	/**
	 * 多文件上�?
	 */
	public String uploadFiles(Integer productid, File[] upload, String[] uploadFileName){
		String pics = "";
		//判断是否有文�?
		if(upload != null){
			for(int i = 0; i < upload.length; i++){
				String pic = productid + "_" + i + uploadFileName[i].substring(uploadFileName[i].lastIndexOf("."));
				String fileurl = productinfoImgUrl + pic;
				fileUpload(upload[i], fileurl);
				if (i != upload.length - 1){
					pics += pic + "|";
				}else{
					pics += pic;
				}
			}
		}
		System.out.println(pics);
		return pics;
	}
	
	
	/**
	 * 文件上传
	 */
	public static  boolean fileUpload(File upload, String fileurl){
		//判断是否有文�?
		if (upload != null){
			try {
				//输出流�?输入�?
				FileOutputStream fos = new FileOutputStream(fileurl);
				FileInputStream fis = new FileInputStream(upload);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer))>0){
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 删除文件
	 */
	public boolean deleteFile(String url){
		boolean flag = false;  
		File file = new File(url);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}
	
	/* lq */
	
}
