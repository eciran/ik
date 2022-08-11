package com.epocale.ik.service;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epocale.ik.entity.UserInOutEntity;
import com.epocale.ik.model.OperationModel;

@Service
public class PuantajService {

	@Autowired
	UserInOutService inOutService;
	
	public void createPuantaj(OperationModel operation) {
		List<UserInOutEntity> inOuntList=inOutService.getInOutList(operation);
		for (UserInOutEntity userInOutEntity : inOuntList) {
			System.out.println("Code"+userInOutEntity.getEmployeeCode().getEmployeeCode()+" Gün:"+userInOutEntity.getCheckInTime().substring(0,2)+" Mesai:"+userInOutEntity.getShift());
		}
		
	}	
}
