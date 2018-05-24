package com.tdhy.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdhy.domain.Department;
import com.tdhy.domain.Employee;
import com.tdhy.repository.DeparmentRepository;
import com.tdhy.repository.EmployeeRepository;
import com.tdhy.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeRepository  employeeRepository;

	@Autowired
	DeparmentRepository deparmentRepository;
	
	@Override
	//@Cacheable(key = "T(String).valueOf(#pageNo).concat('-').concat(#pageSize)")
	@Cacheable(keyGenerator="wiselyKeyGenerator")
	public List<Employee> findAll(Integer pageNo,Integer pageSize) {
		//List<Employee> list =  employeeRepository.findAll();

	Order order = new Order(Sort.Direction.DESC, "id");
	Sort sort = new Sort(order);
	 Pageable pageable = new PageRequest(pageNo, pageSize, sort);
	/**
	 * lambda 表达式
	 */
	Page<Employee> page = employeeRepository.findAll((root,query,cb) -> {
	 	return cb.gt(root.get("age"), 25);
	}, pageable);
		log.info("查询的总页数：{}", page.getTotalPages());
		log.info("查询的总记录数：{}", page.getTotalElements());
		log.info("查询的当前第几页：{}", page.getNumber());
		log.info("查询当前页面的集合：{}", page.getContent());
		log.info("查询页面的记录数：{}", page.getNumberOfElements());
		// for (Employee e : page) {
// log.info("page employee:{}", e);
// }
		List<Employee> list = page.getContent();	
		log.debug("Employee findAll :{}",list);
		return list;
	}

	@Override
	@Cacheable(key = "#id")
	public Employee findOne(Long id) {
		Employee employee = employeeRepository.findOne(id);
		return employee;
	}

	@Override
	@Transactional
	@CacheEvict(key="#Employee.id", allEntries=true)
	public void save(Employee Employee) {
		employeeRepository.save(Employee);
	}

	@Override
	@Transactional
	@CacheEvict(key="#id", allEntries=true)
	public void remove(Long id) {
		
		 employeeRepository.delete(id);
	}

	@Override
	@Transactional
	public void test01() {
		
		int count = 10000;
		for(int i= 0 ;i<=count ;i++) {
			Department dep = new Department();
			dep.setName("部门"+i);
			deparmentRepository.save(dep);
			for(int j=0 ;j<10;j++) {
				Employee emp = new Employee();
				emp.setAge(j+20);
				emp.setSex(1);
				emp.setDepartment(dep);
				emp.setName("员工"+j);
				employeeRepository.save(emp);
				log.debug("employee : {}",emp);
			}
			log.debug("department : {}",dep);
		}
		
	}

	@Override
	public String testCache() {
		return "cache";
	}

}
