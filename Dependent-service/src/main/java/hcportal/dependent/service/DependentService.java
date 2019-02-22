package hcportal.dependent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import hcportal.dependent.entity.Dependent;
import hcportal.dependent.repository.DependentRepository;

@Service
public class DependentService {

	@Autowired
	DependentRepository repository;

	@Cacheable("getDependentById")
	public Dependent getDependentById(int dependentId) {
		return this.repository.findById(dependentId).get();
	}

	@Cacheable("getDependentsByEmployeeId")
	@HystrixCommand(fallbackMethod = "getDependentsByEmployeeIdFallback")
	public List<Dependent> getDependentsByEmployeeId(int employeeId) {
		return this.repository.findByEmployeeId(employeeId);
	}

	@Cacheable("addDependent")
	@HystrixCommand(fallbackMethod = "addDependentFallback")
	public Dependent addDependent(Dependent dependent) {
		Dependent dependentNew = this.repository.save(dependent);
		return dependentNew;
	}

	@Cacheable("updateDependent")
	public Dependent updateDependent(int dependentId, Dependent dependent) {
		Dependent dependentNew = this.repository.findById(dependentId).get();
		if (dependentNew != null) {
			dependentNew.setFirstName(dependent.getFirstName());
			dependentNew.setLastName(dependent.getLastName());
			dependentNew.setAddress1(dependent.getAddress1());
			dependentNew.setAddress2(dependent.getAddress2());

			return this.repository.save(dependentNew);
		}

		return null;
	}

	public List<Dependent> getDependentsByEmployeeIdFallback(int employeeId) {

		List<Dependent> dependents = new ArrayList<Dependent>();
		return dependents;

	}

	public Dependent addDependentFallback(Dependent dependent) {
		return null;
	}

}
