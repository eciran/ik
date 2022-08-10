package com.epocale.ik.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epocale.ik.entity.UserInOutEntity;
import com.epocale.ik.model.OperationModel;
import com.epocale.ik.service.UserInOutService;

@RestController
public class MainController {
	
	@Autowired
	private UserInOutService userInOutService;
	
	  @PostMapping("/upload") 
	  public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file ) {
		String path="src\\main\\resources\\static\\upfiles\\";
	    String fileName = file.getOriginalFilename();
	    try {    	
	    	 File convertFile = new File(path+fileName);
	         convertFile.createNewFile();
	         FileOutputStream fout = new FileOutputStream(convertFile);
	         fout.write(file.getBytes());
	         fout.close();	
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    } 
	    return ResponseEntity.ok("File uploaded successfully.");
	  }
	  
	  @PostMapping("/updatePuantajLine")
	  public String updatePuantajLine(@RequestBody OperationModel operation) {
		  String ret="";
		  try {
			ret=userInOutService.updatePuantajLine(operation);
		} catch (Throwable e) {
			String[] errmsg=e.getMessage().split(":");
			ret=errmsg[1];
		}
		  return ret;
	  }
	  @PostMapping("/getPeriodCount")
	  public String getPeriodCount(@RequestBody OperationModel op) {
		  return userInOutService.getPeriodCount(op);
	  }
	  
	  @PostMapping("/getInOutList")
	  public  List<UserInOutEntity> getInOutList(@RequestBody OperationModel operation) {
		  return userInOutService.getInOutList(operation);
		   
	  }
	 
}
