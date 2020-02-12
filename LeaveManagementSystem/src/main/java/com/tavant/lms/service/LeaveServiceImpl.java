package com.tavant.lms.service;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tavant.lms.dto.EmployeeDTO;
import com.tavant.lms.dto.LeaveDTO;
import com.tavant.lms.entity.Employee;
import com.tavant.lms.entity.Leave;
import com.tavant.lms.exceptions.DataConflictException;
import com.tavant.lms.exceptions.DataNotFoundException;
import com.tavant.lms.exceptions.UnauthorizedRequest;
import com.tavant.lms.mapper.LeaveMapper;
import com.tavant.lms.repository.LeaveRepository;
import com.tavant.lms.security.ExtractUserAuthentication;
import com.tavant.lms.util.DateUtil;
import com.tavant.lms.util.ExceptionConstants;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository employeeLeaveRepository;

    public LeaveServiceImpl(final LeaveRepository employeeLeaveRepository){
        this.employeeLeaveRepository = employeeLeaveRepository;
    }

    @Override
    public Page<LeaveDTO> getAllEmployeeLeaves(Pageable pageable) {

        return employeeLeaveRepository.findAll(pageable)
                .map(employeeLeave -> LeaveMapper.mapToDto(employeeLeave));
    }

    @Override
    public LeaveDTO getEmployeeLeaveById(Long id) {

        Leave employeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
        return LeaveMapper.mapToDto(employeeLeave);
    }

    @Override
    public LeaveDTO createEmployeeLeave(LeaveDTO leaveDTO) {

        //if(leaveDTO.getLeaveTypeDTO() == null){
            //throw new DataNotFoundException(ExceptionConstants.LEAVE_TYPE_RECORD_NOT_FOUND);
        //}
        // Setting current employee id on current newly created Leave Request
    	System.out.println(leaveDTO);
        long employeeId = ExtractUserAuthentication.getCurrentUser().getId();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employeeId);

        leaveDTO.setEmployeeDTO(employeeDTO);
        leaveDTO.setStatus(leaveDTO.getStatus());
        Leave employeeLeave = employeeLeaveRepository.save(LeaveMapper.mapToEntity(leaveDTO));
        return LeaveMapper.mapToDto(employeeLeave);
    }

    @Override
    public LeaveDTO updateEmployeeLeave(LeaveDTO leaveDTO) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(leaveDTO.getLeaveId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        // Leave status must be in pending
        if(returnedEmployeeLeave.getToManager() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        returnedEmployeeLeave.setFromDate(leaveDTO.getFromDate());
        returnedEmployeeLeave.setToDate(leaveDTO.getToDate());
        returnedEmployeeLeave.setLeaveReason(leaveDTO.getLeaveReason());
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    @Override
    public LeaveDTO approveEmployeeLeave(LeaveDTO leaveDTO) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(leaveDTO.getLeaveId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));
        Employee employeeSupervisor = returnedEmployeeLeave.getEmployee().getManager();

        long approverId = ExtractUserAuthentication.getCurrentUser().getId();
        String approverRole = ExtractUserAuthentication.getCurrentUser().getAuthorities().iterator().next().toString();

        // Setting Approver Id extracting from ExtractUserAuthentication currentUser id
        Employee approverEmployee = new Employee();
        approverEmployee.setEmployeeId(approverId);

        // Employee cant approve their own request
        // If employee is user then must be his supervisor
        if(!approverRole.equals("ROLE_ADMIN") || approverId == returnedEmployeeLeave.getEmployee().getEmployeeId()){

            if(approverId == returnedEmployeeLeave.getEmployee().getEmployeeId() ||
                    employeeSupervisor == null || employeeSupervisor.getEmployeeId() == null ||
                    approverId != employeeSupervisor.getEmployeeId()){
                throw new UnauthorizedRequest(ExceptionConstants.YOU_CANT_REVIEW_THIS_REQUEST);
            }
//            throw new UnauthorizedRequest(ExceptionConstants.YOU_CANT_REVIEW_THIS_REQUEST);
        }
        returnedEmployeeLeave.setStatus(leaveDTO.getStatus());
        returnedEmployeeLeave.setToManager(approverEmployee);
        returnedEmployeeLeave.setDeniedReason(leaveDTO.getDeniedReason());
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    /**
     * Delete Leave request that is still pending
     * Leave Record must be present in database else throws Exception
     * Leave status must be in pending else throws Exception
     *
     * @param id
     * @return boolean value of Leave Deletion
     */
    @Override
    public LeaveDTO ChangeEmployeeLeaveStatus(Long id, String status) {

        Leave returnedEmployeeLeave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionConstants.EMPLOYEE_LEAVE_RECORD_NOT_FOUND));

        // Leave status must be in pending
        if(returnedEmployeeLeave.getToManager() != null){
            throw new DataConflictException(ExceptionConstants.EMPLOYEE_LEAVE_ACTION_ALREADY_TAKEN);
        }
        returnedEmployeeLeave.setStatus(status);
        return LeaveMapper.mapToDto(employeeLeaveRepository.save(returnedEmployeeLeave));
    }

    @Override
    public List<LeaveDTO> retrieveEmployeeLeaveByDate(String date1, String date2) {
        return employeeLeaveRepository.findLeaveByDate(DateUtil.convertToDate(date1), DateUtil.convertToDate(date2))
                .stream()
                .map(leave -> LeaveMapper.mapToDto(leave))
                .collect(Collectors.toList());
    }
}
