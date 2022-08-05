package com.epocale.ik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.epocale.ik.entity.EmployeesEntitiy;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesEntitiy, Long> {
	
	@Query(nativeQuery = true,value = "Select * from employees where employee_code	= :employee_code")
	public EmployeesEntitiy findEmployeeByEmployeeId(@Param("employee_code") String employeeCode );
}
