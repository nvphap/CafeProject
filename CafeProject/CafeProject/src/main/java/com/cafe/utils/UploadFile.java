package com.cafe.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

/**
* Server Upload/Download file
*
* @author  	dat.huynh
* @version 	1.0
* @since   	2015-10-16 
* @update   
*/
public class UploadFile {
	
	private final static String DIRECTORY_UPLOAD_FILE = System.getProperty("catalina.home");
	private final static String UPLOAD_FOLDER = "1manHospitalFiles";
			
	/**
	* Upload file to server 
	*
	* @author  	dat.huynh
	* @version 	1.0
	* @since   	2015-10-16
	* @update   
	*/
	public boolean uploadFileHandler(String name, MultipartFile file) {
 
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                //Creating the directory to store file
                String rootPath = DIRECTORY_UPLOAD_FILE;
                File dir = new File(rootPath + File.separator + UPLOAD_FOLDER);
                if (!dir.exists())
                	dir.mkdirs();
 
                //Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
	
	/**
	* Download file from server 
	*
	* @author  	dat.huynh
	* @version 	1.0
	* @since   	2015-10-16
	* @update   
	*/
	public void downloadFileHandler(String fileName, HttpServletRequest request, HttpServletResponse response) {
	    try {
	    	String rootPath = DIRECTORY_UPLOAD_FILE; 
	    	rootPath += File.separator + UPLOAD_FOLDER;
	    	rootPath += File.separator + fileName;
	    	
	    	ServletContext servletContext = request.getSession().getServletContext();
	    	FileInputStream inputStream = null;
	        File fileToDownload = new File(rootPath);
	        String mimetype = servletContext.getMimeType(rootPath);
	        inputStream = new FileInputStream(fileToDownload);
	        
	        response.setContentLength((int) fileToDownload.length());
	        response.setContentType(mimetype);
			response.setContentType("application/force-download");
	        response.setHeader("Content-Disposition", "attachment; filename=" + fileName); 
	        response.setCharacterEncoding(Constant.CHARSET_UTF_8);
	        
	        IOUtils.copy(inputStream, response.getOutputStream());
	        response.flushBuffer();
	        inputStream.close();
	    } catch (Exception e){
	        
	    }
	}
	
}
