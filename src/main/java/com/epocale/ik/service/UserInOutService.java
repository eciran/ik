package com.epocale.ik.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.epocale.ik.entity.EmployeesEntitiy;
import com.epocale.ik.entity.UserInOutEntity;
import com.epocale.ik.model.FileListTO;
import com.epocale.ik.model.OperationModel;
import com.epocale.ik.repository.EmployeesRepository;
import com.epocale.ik.repository.UserInOutRepository;

@Service
public class UserInOutService {

	@Autowired
	UserInOutRepository userInOutRepository;
	
	@Autowired
	EmployeesRepository employeesRepository;
	
	
	public String EmployeeInOutAdd(List<String> userList, String month, String year) throws Exception{
		String ret="";
		String pattern = "dd-MM-yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date inDate = null;
		Date outDate = null;
		try {
			userList.set(3,userList.get(3).replace(".","-"));
			userList.set(6,userList.get(3).replace(".","-"));
			inDate=simpleDateFormat.parse(userList.get(3)+" "+userList.get(5));
			outDate = simpleDateFormat.parse(userList.get(6)+" "+userList.get(8));
			System.out.println("INDATE :" + inDate);
		} catch (ParseException e) {
			System.out.println("Date Format Hatası");
			e.printStackTrace();
		}
		EmployeesEntitiy employee=employeesRepository.findEmployeeByEmployeeId(userList.get(0));
		UserInOutEntity userInOut=new UserInOutEntity();
		if (employee!=null) {
			try {
				userInOut.setEmployeeCode(employee);
				userInOut.setCheckInTime(inDate);	
				userInOut.setCheckOutTime(outDate);
				userInOut.setCheckDate(userList.get(4));
				userInOut.setMonth(month);
				userInOut.setYear(year);
				try {
					userInOutRepository.save(userInOut);
				} catch (Exception e) {
					ret=(userList.get(0)+" Nolu kullanıcının "+inDate+" Giriş tarihli kaydı eklenemedi");
					System.out.println("Kayıt eklenemedi");
				}
			} catch (Exception e) {
				System.out.println("Giriş Çıkış Kaydı Bulunamadı");
			}
		}	
		else {
			System.out.println(userList.get(0) + "Nolu Çalışan bulunamadı");
			ret=(userList.get(0)+"-");
			throw new Exception(ret);
		}
		return ret;
	}


	public String updatePuantajLine(OperationModel operation) throws Throwable {
	  String ret="";
	  List<FileListTO> fileList =fileListShow("puantaj"); 
	  File file=new  File(fileList.get(0).getPath());
	  try (FileReader fr = new FileReader(file);
			  BufferedReader br = new BufferedReader(fr);) {
			  String line;
			  while ((line = br.readLine()) != null) {
				List<String> inOutList =new ArrayList<String>();
				String[] lineInfo=line.split(" ");
				for (int i = 0; i < lineInfo.length; i++) {
					if(lineInfo[i].trim().length()>0) {
						boolean rgx=isNumberFourDigits(lineInfo[6]);
						if(rgx) {
							if(lineInfo[i].contains("Saati")) {
								lineInfo[i]=lineInfo[i].replace("Saati","");
							}
							inOutList.add(lineInfo[i]);
						}
					}
				}
				if(inOutList.size()>0) {
					try {
						ret+=this.EmployeeInOutAdd(inOutList,operation.getMonth(),operation.getYear());
						System.out.println(inOutList);
					} catch (Throwable e) {
						throw new Exception(e);
					}
					
				}
			  }
			} catch (IOException e) {
			  e.printStackTrace();
			}
	  return ret;
	}
	 private List<FileListTO> fileListShow(String op){
		  List<FileListTO> puantajList = null;
		  String path="src\\main\\resources\\static\\upfiles\\";
		  File mainPath=new File(path);
		  File[] fileList=mainPath.listFiles();
		  if(fileList!=null) {
			  puantajList= new ArrayList<FileListTO>();				
	          List<File> flist=new ArrayList<File>();
       		for (File file :fileList) {
       			flist.add(file);
       		}
       		Collections.sort(flist);
       		if(op.equals("puantaj")) {
	        		for (File file : flist) {
	        			FileListTO ret = new FileListTO();
	        			if(file.isFile()){      				
	        				ret.setPath(file.getAbsolutePath());
	        				ret.setType("directory");
	        				puantajList.add(ret);
	        			}
	     			}
       		}
		  }	  

		  return puantajList;
	  }
	  
	  private boolean isNumberFourDigits(String password) {
			return Pattern.compile("^\\d{4}$")
					.matcher(password).matches();
		}
}
