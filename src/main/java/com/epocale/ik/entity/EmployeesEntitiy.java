package com.epocale.ik.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Employees")
public class EmployeesEntitiy {
	
	@Id
	@Column(unique = true)
	private String employeeCode;
	private String employeeName;
	private String employeeSecondName;
	private String employeeLastName;
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String emplooyeCode) {
		this.employeeCode = emplooyeCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeSecondName() {
		return employeeSecondName;
	}
	public void setEmployeeSecondName(String employeeSecondName) {
		this.employeeSecondName = employeeSecondName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	
	
}
