package hcportal.microservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hcportal.microservice.entity.Employee;
import hcportal.microservice.service.EmployeeService;

@RestController
@RequestMapping("/healthcare/v1/employeeOps")
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@RequestMapping(method = RequestMethod.GET, value = "/employee/{id}", produces = "application/json")
	public Optional<Employee> getEmployee(@PathVariable("id") int employeeId) {
		return this.service.getEmployeeById(employeeId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/employee", consumes = "application/json")
	public Employee addEmployee(@RequestBody Employee employee) {
		Employee employeeNew = this.service.addEmployee(employee);
		return employeeNew;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/employee/{id}", consumes = "application/json", produces = "application/json")
	public Employee updateEmployee(@PathVariable("id") int employeeId, @RequestBody Employee employee) {
		Employee employeeUpdated = this.service.updateEmployee(employeeId, employee);

		return employeeUpdated;
	}

}
