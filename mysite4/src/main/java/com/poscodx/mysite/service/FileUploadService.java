package com.poscodx.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:com/poscodx/mysite/config/web/fileupload.properties")
public class FileUploadService {
	
	@Autowired
	private Environment env;

	public String restore(MultipartFile file) {
		System.out.println("## file:  " + file);
		String url = null;
		
		try {
			File uploadDirectory = new File(env.getProperty("fileupload.uploadLacation"));
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}
			
			if(file.isEmpty()) {
				return url;
			}
			
			String originalFilename = file.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			String saveFilename = generateSaveFilename(extName);
			// Long fileSize = file.getSize();
			
			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(env.getProperty("fileupload.uploadLacation") + "/" + saveFilename);
			os.write(data);
			os.close();
			
			url = env.getProperty("fileupload.resourceUrl") + "/" + saveFilename;
			
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
		
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}
	

}
