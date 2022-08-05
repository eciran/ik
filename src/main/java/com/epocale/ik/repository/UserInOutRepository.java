package com.epocale.ik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epocale.ik.entity.UserInOutEntity;

@Repository
public interface UserInOutRepository extends JpaRepository<UserInOutEntity,Long>{

}
