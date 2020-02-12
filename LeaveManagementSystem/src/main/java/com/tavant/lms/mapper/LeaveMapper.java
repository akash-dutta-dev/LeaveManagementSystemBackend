package com.tavant.lms.mapper;

import com.tavant.lms.dto.LeaveDTO;
import com.tavant.lms.entity.Leave;

public class LeaveMapper {

    public static LeaveDTO mapToDto(Leave leave){
        LeaveDTO leaveDTO = new LeaveDTO();
        leaveDTO.setLeaveId(leave.getLeaveId());
        if(leave.getEmployee() != null) {
        	leaveDTO.setEmployeeDTO(EmployeeMapper.mapToDto(leave.getEmployee()));
        }
        leaveDTO.setLeaveType(leave.getLeaveType());
        leaveDTO.setLeaveReason(leave.getLeaveReason());
        leaveDTO.setFromDate(leave.getFromDate());
        leaveDTO.setToDate(leave.getToDate());
        leaveDTO.setDeniedReason(leave.getDeniedReason());
        leaveDTO.setStatus(String.valueOf(leave.getStatus()));
        leaveDTO.setCreatedAt(leave.getCreatedAt());
        if(leave.getToManager() != null) {
        	leaveDTO.setToManager(EmployeeMapper.mapToDto(leave.getToManager()));
        }
        return leaveDTO;
    }

    public static Leave mapToEntity(LeaveDTO leaveDTO){
        Leave leave = new Leave();
        leave.setLeaveId(leaveDTO.getLeaveId());
        if(leaveDTO.getEmployeeDTO() != null) {
        	leave.setEmployee(EmployeeMapper.mapToEntity(leaveDTO.getEmployeeDTO()));
        }
        leave.setLeaveType(leaveDTO.getLeaveType());
        leave.setLeaveReason(leaveDTO.getLeaveReason());
        leave.setFromDate(leaveDTO.getFromDate());
        leave.setToDate(leaveDTO.getToDate());
        leave.setDeniedReason(leaveDTO.getDeniedReason());
        leave.setStatus(leaveDTO.getStatus());
        leave.setCreatedAt(leaveDTO.getCreatedAt());
        if(leaveDTO.getToManager() != null) {
        	leave.setToManager(EmployeeMapper.mapToEntity(leaveDTO.getToManager()));
        }
        return leave;
    }
}
