package com.tdhy.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdhy.domain.Department;
import com.tdhy.repository.DeparmentRepository;
import com.tdhy.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DeparmentRepository  departmenRepository;

	@Override
	public List<Department> findAll() {
		List<Department> list =  departmenRepository.findAll();
		log.debug("Department findAll :{}",list);
		return list;
	}

	@Override
	public Department findOne(Long id) {
		Department department = departmenRepository.findOne(id);
		return department;
	}

	@Override
	@Transactional
	public void save(Department Department) {
		departmenRepository.save(Department);
	}

	@Override
	@Transactional
	public void remove(Long id) {
		
		departmenRepository.delete(id);
	}

}
