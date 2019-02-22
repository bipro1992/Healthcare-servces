package hcportal.dependent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hcportal.dependent.entity.Dependent;
import hcportal.dependent.service.DependentService;

@RestController
@RequestMapping("/healthcare/v1/dependentOps")
public class DependentController {

	@Autowired
	private DependentService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/dependent/{id}",produces="application/json")
	public Dependent getDependentById(@PathVariable("id") int dependentId)
	{
		return this.service.getDependentById(dependentId);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/dependent/employee/{employeeId}",produces="application/json")
	public List<Dependent> getDependentsByEmployeeId(@PathVariable("employeeId") int employeeId)
	{
		return this.service.getDependentsByEmployeeId(employeeId);
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/dependent",consumes="application/json")
	public Dependent addDependent(@RequestBody Dependent dependent)
	{
		Dependent dependentNew=this.service.addDependent(dependent);
		return dependentNew;
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/dependent/{id}", consumes = "application/json", produces = "application/json")
	public Dependent updateDependent(@PathVariable("id") int dependentId,@RequestBody Dependent dependent)
	{
		Dependent dependentUpdated= this.service.updateDependent(dependentId, dependent);
		return dependentUpdated;
	}
}
