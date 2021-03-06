package com.tavant.lms.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tavant.lms.dto.LeaveDTO;
import com.tavant.lms.service.LeaveService;

@RestController
@RequestMapping("/rest/employee-leaves")
public class LeaveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveController.class);

    private final LeaveService employeeLeaveService;

    public LeaveController(final LeaveService employeeLeaveService) {
        this.employeeLeaveService = employeeLeaveService;
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllEmployeeLeaves(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        LOGGER.info("API Retrieve all EmployeeLeaves");
        return new ResponseEntity<>(employeeLeaveService.getAllEmployeeLeaves(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveEmployeeLeave(@PathVariable long id){

        LOGGER.info("API Retrieve single Leave");
        return new ResponseEntity<>(employeeLeaveService.getEmployeeLeaveById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEmployeeLeave(@RequestBody LeaveDTO leaveDTO){
    	
    	System.out.println(leaveDTO);

        LOGGER.info("API Create Leave Request");
        return new ResponseEntity<>(employeeLeaveService.createEmployeeLeave(leaveDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateEmployeeLeave(@RequestBody LeaveDTO leaveDTO){

        LOGGER.info("API update Leave");
        return new ResponseEntity<>(employeeLeaveService.updateEmployeeLeave(leaveDTO), HttpStatus.OK);
    }

    @PutMapping("/approve-employee-leave")
    public ResponseEntity<?> approveEmployeeLeave(@RequestBody LeaveDTO leaveDTO){

        LOGGER.info("API Approve Leave");
        return new ResponseEntity<>(employeeLeaveService.approveEmployeeLeave(leaveDTO), HttpStatus.OK);
    }

    @DeleteMapping("/change-request-status")
    public ResponseEntity<?> ChangeEmployeeLeaveStatus(@RequestParam("id") long id, @RequestParam("status") String status){

        LOGGER.info("API Delete Pending Leave Request");
        return new ResponseEntity<>(employeeLeaveService.ChangeEmployeeLeaveStatus(id, status), HttpStatus.OK);
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> retrieveEmployeeLeaveByDate(
            @RequestParam("date1") String date1,
            @RequestParam("date2") String date2){
        LOGGER.info("API Retrieve Leave By Date");
        return new ResponseEntity<>(employeeLeaveService.retrieveEmployeeLeaveByDate(date1, date2), HttpStatus.OK);
    }
}
