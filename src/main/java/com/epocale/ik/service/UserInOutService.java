package com.epocale.ik.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		EmployeesEntitiy employee=employeesRepository.findEmployeeByEmployeeId(userList.get(0));
		UserInOutEntity userInOut=new UserInOutEntity();
		if (employee!=null) {
			Integer[] sch;
			if(employee.getEmployeeSecondName()==null) {
				try {		
					userList.set(3,userList.get(3).replace(".","-"));
					userInOut.setEmployeeCode(employee);
					userInOut.setCheckInTime(userList.get(3));
					userInOut.setCheckDate(userList.get(4));
					userInOut.setCheckInHour(userList.get(5));
					userInOut.setMonth(month);
					userInOut.setYear(year);
					if(userList.size()>6) {
						sch=this.calculateShift(userList.get(5),userList.get(8),userList.get(4));
						userInOut.setShift(sch[0]);
						userInOut.setCuts(sch[1]);
						userList.set(6,userList.get(6).replace(".","-"));
						userInOut.setCheckOutTime(userList.get(6));
						userInOut.setCheckOutHour(userList.get(8));
					}
					else {
						userInOut.setShift(99999);
						userInOut.setCuts(99999);
						userInOut.setCheckOutTime("-");
						userInOut.setCheckOutHour("-");
					}
					try {
						userInOutRepository.save(userInOut);
					} catch (Exception e) {
						ret=(userList.get(0)+" Nolu kullanıcının "+userList.get(3)+" Giriş tarihli kaydı eklenemedi");
						System.out.println("Kayıt eklenemedi");
					}
				} catch (Exception e) {
					System.out.println("Giriş Çıkış Kaydı Bulunamadı");
				}
			}
			else {
				try {
					userList.set(4,userList.get(4).replace(".","-"));
					userInOut.setEmployeeCode(employee);
					userInOut.setCheckInTime(userList.get(4));
					userInOut.setCheckDate(userList.get(5));
					userInOut.setCheckInHour(userList.get(6));
					userInOut.setMonth(month);
					userInOut.setYear(year);
					if(userList.size()>7) {
						sch=this.calculateShift(userList.get(6),userList.get(9), userList.get(5));
						userInOut.setShift(sch[0]);
						userInOut.setCuts(sch[1]);
						userList.set(7,userList.get(7).replace(".","-"));
						userInOut.setCheckOutTime(userList.get(7));
						userInOut.setCheckOutHour(userList.get(9));
					}
					else {
						userInOut.setShift(99999);
						userInOut.setCuts(99999);
						userInOut.setCheckOutTime("-");
						userInOut.setCheckOutHour("-");
					}
					try {
						userInOutRepository.save(userInOut);
					} catch (Exception e) {
						ret=(userList.get(0)+" Nolu kullanıcının "+userList.get(3)+" Giriş tarihli kaydı eklenemedi");
						System.out.println("Kayıt eklenemedi");
					}
				} catch (Exception e) {
					ret=userList.get(0)+" Nolu kullanıcının Veri Ekleme Hatası";
					System.out.println("Giriş Çıkış Kaydı Bulunamadı");
				}
			}
		}	
		else {
//			EmployeesEntitiy employee1=new EmployeesEntitiy();
//			employee1.setEmployeeCode(userList.get(0));
//			employee1.setEmployeeName(userList.get(1));
//			employee1.setEmployeeLastName(userList.get(2));
//			employeesRepository.save(employee1);
			System.out.println(userList.get(0) + "Nolu Çalışan bulunamadı");
			ret=(userList.get(0)+"-");
			throw new Exception(ret);
		}
		return ret;
	}


	public String addInOutLine(OperationModel operation) throws Throwable {
	  String ret="";
	  try {
			userInOutRepository.deletePeriod(operation.getMonth(), operation.getYear());
	  }catch (Exception e) {
		  e.printStackTrace();
		  System.out.println("Veri Silinemedi");
	  }
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
	  try {
		this.deleteInOutFiles();
	} catch (Exception e) {
		System.out.println("Dosya Silinemedi");
	}
	  return ret;
	}
	
	 private void deleteInOutFiles() {
		 List<FileListTO> fileList =fileListShow("puantaj"); 
		  File file=new  File(fileList.get(0).getPath());
		  Path path=Paths.get(file.getAbsolutePath());
		  try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


	public List<UserInOutEntity> getInOutList(OperationModel operation) {
		return userInOutRepository.findAllByMonthAndYear(operation.getMonth(),operation.getYear());
	}
	
	public List<UserInOutEntity> getListEmpCodeAndInTime(OperationModel operation) {
		return userInOutRepository.findAllByMonthAndYear(operation.getMonth(),operation.getYear());
	}


	public String getPeriodCount(OperationModel op) {
		String month=op.getMonth();
		String year=op.getYear();
		return userInOutRepository.getPeriodCount(month,year);
	}
	
	private Integer[] calculateShift(String in_hour, String out_hour, String in_date) {
		Integer[] ret=new Integer[2];
		int shift=0;
		int cuts=0;
		int _in_hour=450;
		int _out_hour=1080;
		if(in_hour.contains(":")&&out_hour.contains(":")) {
			String[] external_in_time=in_hour.split(":");
			String[] external_out_time=out_hour.split(":");
			if(external_in_time[0]!="-"&&external_in_time[1]!="-"&&external_out_time[0]!="-"&&external_out_time[1]!="-") {
				int _inTime=Integer.parseInt(external_in_time[0])*60+Integer.parseInt(external_in_time[1]);			
				int _outTime=Integer.parseInt(external_out_time[0])*60+Integer.parseInt(external_out_time[1]);
				if(in_date.equals("Cmt") || in_date.equals("Paz")) {
					double deger=_outTime-_inTime;
					shift+=Math.round(deger/60);
				}
				else {
					if(_inTime>_in_hour) {
						double deger=_inTime-_in_hour;
						cuts+=Math.round(deger/60);
					}
					if(_outTime>_out_hour){
						double deger=_outTime-(_out_hour-30);
						shift+=Math.round(deger/60);;
					}
					else if(_outTime<_out_hour){
						if(!(_outTime>=(_out_hour-30))) {
							double deger=(_out_hour-30)-_outTime;
							cuts+=Math.round(deger/60);
						}
					}					
					System.out.println("Shift:"+shift);
				}
			}else {
				System.out.println("Shift:"+shift);
			}
		}else {
			System.out.println("Shift:"+shift);
		}	
		ret[0]=shift;
		ret[1]=cuts;
		return ret;
	}
}
