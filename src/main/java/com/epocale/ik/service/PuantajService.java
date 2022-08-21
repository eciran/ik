package com.epocale.ik.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epocale.ik.entity.UserInOutEntity;
import com.epocale.ik.model.CodeTimeTO;
import com.epocale.ik.model.OperationModel;

@Service
public class PuantajService {

	@Autowired
	UserInOutService inOutService;
	
	public List<String> createPuantaj(OperationModel operation) {
		List<UserInOutEntity> inOuntList=inOutService.getInOutList(operation);
		String emp="";
		String ret=""; 
		String empName="";
		List<String> addList=new ArrayList<String>();
		int shift=0;
		int cuts=0;
		for (UserInOutEntity userInOutEntity : inOuntList) {
			if(emp.length()==0) {
				emp=userInOutEntity.getEmployeeCode().getEmployeeCode();
				if(userInOutEntity.getEmployeeCode().getEmployeeSecondName()!=null) {
					empName=userInOutEntity.getEmployeeCode().getEmployeeName()+" "+userInOutEntity.getEmployeeCode().getEmployeeSecondName()+" "+userInOutEntity.getEmployeeCode().getEmployeeLastName();
				}
				else {
					empName=userInOutEntity.getEmployeeCode().getEmployeeName()+" "+userInOutEntity.getEmployeeCode().getEmployeeLastName();
				}
				ret=empName+"-";
			}
			if(emp.equals(userInOutEntity.getEmployeeCode().getEmployeeCode())) {
				ret+=userInOutEntity.getCheckInTime().substring(0,2)+"-";
				if(userInOutEntity.getShift()!=99999) {
					shift+=userInOutEntity.getShift();
				}
				if(userInOutEntity.getCuts()!=99999) {
					cuts+=userInOutEntity.getCuts();
				}
			}
			else {
				ret+="shift:"+shift+"-cuts:"+cuts;
				cuts=0;shift=0;
				addList.add(ret);
				if(userInOutEntity.getEmployeeCode().getEmployeeSecondName()!=null) {
					empName=userInOutEntity.getEmployeeCode().getEmployeeName()+" "+userInOutEntity.getEmployeeCode().getEmployeeSecondName()+" "+userInOutEntity.getEmployeeCode().getEmployeeLastName();
				}
				else {
					empName=userInOutEntity.getEmployeeCode().getEmployeeName()+" "+userInOutEntity.getEmployeeCode().getEmployeeLastName();
				}
				ret=empName+"-";
				emp=userInOutEntity.getEmployeeCode().getEmployeeCode();
				ret+=userInOutEntity.getCheckInTime().substring(0,2)+"-";
			}
			
		}
		    String sDays=this.specialDayOfMonth(operation.getMonth(),operation.getYear());
			addList.add(sDays);
			System.out.println(addList);
			return addList;
	}	
	
	private String specialDayOfMonth(String month,String year) {
		int x=1;
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        String sDays="";
		try {
			date = df.parse(year+month+"01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i <8; i++) {
			if (day%7==6) {
				x+=i;
				break;
			}else {
				day++;
			}
		}
        while(x<31) {
        	if(x<10) {
        		sDays+="0"+x+"-";
        		if(x+1<10) {
        			sDays+="0"+(x+1)+"-";
        		}
        		else {
        			sDays+=x+1+"-";
        		}
        		
        	}
        	else {
        		sDays+=x+"-";
            	sDays+=x+1+"-";
        	}
        	
        	x+=7;
        }	   
        sDays+="HS";
		return sDays;
	}
}
