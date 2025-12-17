package com.abdhu.pemakaian_service.repository;

import com.abdhu.pemakaian_service.model.Pemakaian;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PemakaianRepository extends JpaRepository<Pemakaian, Long> {
	// Basic CRUD operations are provided by JpaRepository
}