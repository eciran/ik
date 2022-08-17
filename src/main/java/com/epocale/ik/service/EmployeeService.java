package com.epocale.ik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epocale.ik.entity.EmployeesEntitiy;
import com.epocale.ik.repository.EmployeesRepository;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeesRepository employeesRepository;
	
	public String addEmployee(EmployeesEntitiy employee) {
		String ret="";
		try {
			boolean isExist=employeesRepository.existsById(employee.getEmployeeCode());
			if(isExist) {
				ret="Exist";
			}else {
				if(employee.getEmployeeSecondName().isEmpty()) {
					employee.setEmployeeSecondName(null);
				}
				employeesRepository.saveAndFlush(employee);
				ret="Success";
			}
			
		} catch (Exception e) {
			ret="Error";
			e.printStackTrace();
		}
		return ret;
	}

	public List<EmployeesEntitiy> getEmployeeList() {
		return employeesRepository.findAll();

	}

	public String updateEmployee(EmployeesEntitiy employee) {
		String ret="";
		try {
			employeesRepository.save(employee);
			ret="Success";
		} catch (Exception e) {
			e.printStackTrace();
			ret="Failed";
		}
		return ret;
	}
}
