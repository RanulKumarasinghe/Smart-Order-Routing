package com.ab.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.ab.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
//	@Modifying
//    @Transactional
//	@Query(value="INSERT INTO user(userfirstName,userLastName)")
}
