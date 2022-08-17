package com.epocale.ik.service;

import java.util.ArrayList;
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
		String a=""; 
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
				a=empName+"-";
			}
			if(emp.equals(userInOutEntity.getEmployeeCode().getEmployeeCode())) {
				a+=userInOutEntity.getCheckInTime().substring(0,2)+"-";
				if(userInOutEntity.getShift()!=99999) {
					shift+=userInOutEntity.getShift();
				}
				if(userInOutEntity.getCuts()!=99999) {
					cuts+=userInOutEntity.getCuts();
				}
			}
			else {
				a+="shift:"+shift+"-cuts:"+cuts;
				cuts=0;shift=0;
				addList.add(a);
				if(userInOutEntity.getEmployeeCode().getEmployeeSecondName()!=null) {
					empName=userInOutEntity.getEmployeeCode().getEmployeeName()+" "+userInOutEntity.getEmployeeCode().getEmployeeSecondName()+" "+userInOutEntity.getEmployeeCode().getEmployeeLastName();
				}
				else {
					empName=userInOutEntity.getEmployeeCode().getEmployeeName()+" "+userInOutEntity.getEmployeeCode().getEmployeeLastName();
				}
				a=empName+"-";
				emp=userInOutEntity.getEmployeeCode().getEmployeeCode();
				a+=userInOutEntity.getCheckInTime().substring(0,2)+"-";
			}
			
		}
			System.out.println(addList);
			return addList;
	}	
}
