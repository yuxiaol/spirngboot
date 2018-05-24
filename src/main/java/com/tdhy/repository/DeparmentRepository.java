package com.tdhy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tdhy.domain.Department;

@Repository
public interface DeparmentRepository extends JpaRepository<Department, Long>,JpaSpecificationExecutor<Department> {
	
}
