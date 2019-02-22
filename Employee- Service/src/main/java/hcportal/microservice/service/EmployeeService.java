package hcportal.microservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import hcportal.microservice.entity.Employee;
import hcportal.microservice.repository.EmployeeRespository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRespository respository;
	

	@Cacheable("EmployeeId")
	public Optional<Employee> getEmployeeById(int employeeId) {
		return this.respository.findById(employeeId);
	}

	@Cacheable("Employee")
	@HystrixCommand(fallbackMethod = "addEmployeeFallback")
	public Employee addEmployee(Employee employee) {
		
		//String url="http://localhost:8089/healthcare/v1/employerOps/employer/"+employee.getEmployerId();
		
		Employee ee = this.respository.save(employee);
		return ee;
	}

	@Cacheable("EmployeeUpdate")
	public Employee updateEmployee(int employeeId, Employee employee) {
		Employee employeeNew = this.respository.findById(employeeId).get();

		if (employeeNew != null) {
			employeeNew.setFirstName(employee.getFirstName());
			employeeNew.setLastName(employee.getLastName());
			employeeNew.setAddress1(employee.getAddress1());
			employeeNew.setAddress2(employee.getAddress2());

			return this.respository.save(employeeNew);
		}

		return null;

	}

	public Employee addEmployeeFallback(Employee employee) {
		return null;

	}

}
