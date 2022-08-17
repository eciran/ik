package com.epocale.ik.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private String checkInTime;
	private String checkOutTime;
	private String checkInHour;
	private String checkOutHour; 
	private String checkDate;
	@Column(name = "month")
	private String month;
	@Column(name = "year")
	private String year;
	private int shift;
	private int cuts;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
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
	public String getCheckInHour() {
		return checkInHour;
	}
	public void setCheckInHour(String checkInHour) {
		this.checkInHour = checkInHour;
	}
	public String getCheckOutHour() {
		return checkOutHour;
	}
	public void setCheckOutHour(String checkOutHour) {
		this.checkOutHour = checkOutHour;
	}
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;
	}
	public int getCuts() {
		return cuts;
	}
	public void setCuts(int cuts) {
		this.cuts = cuts;
	}
	
}
