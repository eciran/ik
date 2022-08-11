package com.epocale.ik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epocale.ik.entity.EmployeesEntitiy;
import com.epocale.ik.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@PostMapping(value = "/addEmployee")
	public String addEmployee(@RequestBody EmployeesEntitiy employee) {
		return employeeService.addEmployee(employee);
	}
	
	@GetMapping(value = "/employeeList")
	public List<EmployeesEntitiy> getEmployeeList(){
		return employeeService.getEmployeeList();
	}
}
