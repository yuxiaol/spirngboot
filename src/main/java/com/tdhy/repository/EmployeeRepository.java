package com.tdhy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tdhy.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> ,JpaSpecificationExecutor<Employee>{
	
}
