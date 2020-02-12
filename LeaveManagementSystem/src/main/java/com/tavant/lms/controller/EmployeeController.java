package com.tavant.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tavant.lms.dto.EmployeeDTO;
import com.tavant.lms.entity.Employee;
import com.tavant.lms.service.EmployeeService;

@RestController
@RequestMapping("/rest/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
    	
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllEmployees(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        LOGGER.info("API Return all Employee´s");
        return new ResponseEntity<>(employeeService.getAllEmployees(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {

        LOGGER.info("API Return created Employee");
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {

        LOGGER.info("API Return updated Employee");
        return new ResponseEntity<>(employeeService.updateEmployee(employeeDTO), HttpStatus.OK);
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> retrieveAuthenticatedEmployee() {

        LOGGER.info("API Return Authenticated Employee Profile");
        return new ResponseEntity<>(employeeService.retrieveAuthenticatedEmployee(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveEmployee(@PathVariable long id) {

        LOGGER.info("API Return single Employee");
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }
    
    @GetMapping("/admin")
    public ResponseEntity<?> retrieveAdmin() {

        LOGGER.info("API Return Admin Employee");
        return new ResponseEntity<>(employeeService.getAllAdmin(), HttpStatus.OK);
    }
    
    @GetMapping("/employee-manager/{id}")
    public ResponseEntity<?> getEmployeeManager(@PathVariable long id){
    	LOGGER.info("API Return Employees's Manager");
    	return new ResponseEntity<>(employeeService.getEmployeeManager(id), HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {

        LOGGER.info("API Updating Employee Password");
        return new ResponseEntity<>(employeeService.updatePassword(oldPassword, newPassword), HttpStatus.OK);
    }

    @GetMapping("/employees-under-supervision/{id}")
    public ResponseEntity<?> retrieveAllEmployeesUnderSupervision(@PathVariable long id) {

        LOGGER.info("API Return all Employees under supervision");
        return new ResponseEntity<>(employeeService.getAllEmployeeUnderSupervision(id), HttpStatus.OK);
    }

    @GetMapping("/employee-by-fullname")
    public ResponseEntity<?> retrieveAllEmployeesByFullName(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam("fullname") String fullname) {

        LOGGER.info("API Return Employee´s By FullName");
        return new ResponseEntity<>(employeeService.getAllEmployeesByName(pageable, fullname).getContent(), HttpStatus.OK);
    }




}
