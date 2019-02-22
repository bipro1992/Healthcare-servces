package hcportal.dependent.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hcportal.dependent.entity.Dependent;

@Repository
public interface DependentRepository extends CrudRepository<Dependent, Integer> {

	public List<Dependent> findByEmployeeId(int employeeId);
}
