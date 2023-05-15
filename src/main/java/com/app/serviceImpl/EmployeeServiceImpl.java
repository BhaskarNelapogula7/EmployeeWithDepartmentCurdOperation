package com.app.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Department;
import com.app.entity.Employee;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.DepartmentRepository;
import com.app.repository.EmployeeRepository;
import com.app.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private DepartmentRepository DepartmentRepository;

	@Override
	public Employee saveEmployee(Employee empObj) {

		return employeeRepo.save(empObj);
	}

	@Override
	public Employee getEmployeeById(Integer id) {

		Optional<Employee> employee = employeeRepo.findById(id);

		if (employee.isPresent()) {

			return employee.get();

		} else {

			throw new ResourceNotFoundException("Record not found with id : " + id);
		}
	}

	@Override
	public List<Employee> getAllEmployees() {

		return employeeRepo.findAll();

	}

	@Override
	public void deleteEmployee(Integer id) {

		// employeeRepo.deleteById(id);

		Optional<Employee> employee = employeeRepo.findById(id);

		if (employee.isPresent()) {

			employeeRepo.delete(employee.get());
		} else {

			throw new ResourceNotFoundException("Record not found with id : " + id);
		}
	}

	@Override
	public Employee findByName(String name) {

		Optional<Employee> employee = employeeRepo.findByName(name);

		if (employee.isPresent()) {

			return employee.get();

		} else {

			throw new ResourceNotFoundException("Record not found with id : " + name);
		}
	}

	@Override
	public Employee updateEmployee(Integer id, Employee empObj) {

		Optional<Employee> existingEmployee = employeeRepo.findById(id);

		Optional<Department> depertament = DepartmentRepository.findById(id);
		
		if (existingEmployee.isPresent()) {

			existingEmployee.get().setName(empObj.getName());
			existingEmployee.get().setDesignation(empObj.getDesignation());
			existingEmployee.get().setSalary(empObj.getSalary());

//			Department department = new Department();
//			department.setName(empObj.getDepartment().getName());
//			
//			existingEmployee.get().setDepartment(department);

			depertament.get().setName(empObj.getDepartment().getName());

		} else {

			throw new ResourceNotFoundException("Record not found with id : " + id);
		}
		return employeeRepo.save(existingEmployee.get());
	}
}
