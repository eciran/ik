package com.epocale.ik.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="InOutList")
public class UserInOutEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="employee_code",
	nullable = false,
	foreignKey = @ForeignKey(name="FK_EMP_CODE"))
	private EmployeesEntitiy employeeCode;
//	private String employeeCode;
	private Date checkInTime;
	private Date checkOutTime;
	private String checkDate;
	private String month;
	private String year;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}
	public Date getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public EmployeesEntitiy getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(EmployeesEntitiy employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
