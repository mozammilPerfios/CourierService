package com.perfios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfios.model.Courier;
import com.perfios.model.User;


@Repository
public interface CourierRepository extends JpaRepository<Courier, Long>{
	User findByCourierId(int id);

	List<Courier> findByUser(User user);

	
}