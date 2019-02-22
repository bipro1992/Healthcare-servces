package hcportal.claimDetail.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import hcportal.claimDetail.dto.Employee;
import hcportal.claimDetail.entity.ClaimDetail;
import hcportal.claimDetail.repository.ClaimDetailRepository;

@Service
public class ClaimDetailService {

	@Autowired
	private ClaimDetailRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Cacheable("claimDetailById")
	public ClaimDetail getClaimDetailById(int claimDetail) {

		return this.repository.findById(claimDetail).get();
	}

	@HystrixCommand(fallbackMethod = "getClaimDetailsByEmployeeIdFallback")
	@Cacheable("ClaimDetailsByEmployeeId")

	public List<ClaimDetail> getClaimDetailsByEmployeeId(int employeeId) {

		String url = "http://localhost:8085/healthcare/v1/employeeOps/employee/" + employeeId;

		List<ClaimDetail> claimDetails = new ArrayList<ClaimDetail>();

		Employee employee = this.restTemplate.getForObject(url, Employee.class);

		try {
			if (employee != null) {
				System.out.println(employee.getFirstName());
				claimDetails = this.repository.findByEmployeeId(employeeId);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return claimDetails;
	}

	@HystrixCommand(fallbackMethod = "addClaimDetailFallback")
	@Cacheable("addClaimDetail")

	public ClaimDetail addClaimDetail(ClaimDetail claimDetail) {

		String url = "http://localhost:8085/healthcare/v1/employeeOps/employee/" + claimDetail.getEmployeeId();

		Employee employee = this.restTemplate.getForObject(url, Employee.class);

		if (employee != null) {

			ClaimDetail claimDetailNew = this.repository.save(claimDetail);
			System.out.println(claimDetailNew);
			return claimDetailNew;
		}
		return null;
	}

	@Cacheable("updateClaimDetail")
	public ClaimDetail updateClaimDetail(int claimDetailId, ClaimDetail claimDetail) {
		ClaimDetail claimDetailNew = this.repository.findById(claimDetailId).get();
		if (claimDetailNew != null) {
			claimDetailNew.setRequestedAmount(claimDetail.getRequestedAmount());
			claimDetailNew.setDeniedAmount(claimDetail.getDeniedAmount());
			return this.repository.save(claimDetailNew);
		}

		return null;
	}

	public List<ClaimDetail> getClaimDetailsByEmployeeIdFallback(int employeeId) {
		System.out.println("inside getClaimDetailsByEmployeeIdFallback");
		List<ClaimDetail> claimDetails = new ArrayList<ClaimDetail>();
		return claimDetails;
	}

	public ClaimDetail addClaimDetailFallback(ClaimDetail claimDetail) {
		System.out.println("inside addClaimDetailFallback");
		return null;
	}
}
