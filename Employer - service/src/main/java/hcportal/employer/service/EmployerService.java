package hcportal.employer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import hcportal.employer.entity.Employer;
import hcportal.employer.repository.EmployerRepository;

@Service
public class EmployerService {

	@Autowired
	private EmployerRepository repository;

	@Cacheable("addEmployer")
	public Employer addEmployer(Employer employer) {
		return this.repository.save(employer);
	}

	@Cacheable("getEmployerById")
	public Employer getEmployerById(int employerId) {
		return this.repository.findById(employerId).get();
	}

	@Cacheable("updateEmployer")
	public Employer updateEmployer(int employerId, Employer employer) {
		Employer employerNew = this.repository.findById(employerId).get();

		if (employer != null) {
			employerNew.setEmployerName(employer.getEmployerName());
			employerNew.setElectionAmount(employer.getElectionAmount());
			return this.repository.save(employerNew);
		}

		return null;
	}

}
