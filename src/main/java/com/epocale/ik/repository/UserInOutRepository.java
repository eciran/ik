package com.epocale.ik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epocale.ik.entity.UserInOutEntity;
import com.epocale.ik.model.OperationModel;

@Repository
public interface UserInOutRepository extends JpaRepository<UserInOutEntity,Long>{
	
	@Query(nativeQuery = true,value = "SELECT * FROM epocale.employees as emp LEFT JOIN epocale.in_out_list as list ON emp.employee_code=list.employee_code where month= :month and year= :year")
	List<UserInOutEntity> findAllByMonthAndYear(@Param("month") String month, @Param("year") String year);

	@Query(nativeQuery = true,value="select count(*) from epocale.in_out_list where month= :month and year= :year")
	String getPeriodCount(@Param("month") String month, @Param("year") String year);

}
