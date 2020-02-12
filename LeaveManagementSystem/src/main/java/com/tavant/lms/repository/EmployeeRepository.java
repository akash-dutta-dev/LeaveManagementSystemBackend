package com.tavant.lms.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tavant.lms.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    Page<Employee> findAll(Pageable pageable);

    Employee findByUsername(String username);
    
    Employee findByManager(Long id);

    // All employee under supervision of given employee
    List<Employee> findAllByManager(Employee employee);
    
    List<Employee> findAllByRole(String role);

    Page<Employee> findByFirstNameContainingOrMiddleNameContainingOrLastNameContaining(Pageable pageable, String firstName, String middleName, String lastName);
}
