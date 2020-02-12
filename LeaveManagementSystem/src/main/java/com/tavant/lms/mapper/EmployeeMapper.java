package com.tavant.lms.mapper;

import com.tavant.lms.dto.EmployeeDTO;
import com.tavant.lms.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO mapToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setUsername(employee.getUsername());
//        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setMiddleName(employee.getMiddleName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPhoneNumber((employee.getPhoneNumber()));
        employeeDTO.setCreatedAt(employee.getCreatedAt());
        return employeeDTO;
    }

    public static Employee mapToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setRole(employeeDTO.getRole());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setMiddleName(employeeDTO.getMiddleName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setCreatedAt(employeeDTO.getCreatedAt());
        return employee;
    }

    public static EmployeeDTO mapToDTOWithSupervisor(Employee employee) {
        EmployeeDTO employeeDTO = mapToDto(employee);
        if (employee.getManager() != null) {
            employeeDTO.setManager(EmployeeMapper.mapToDto(employee.getManager()));
        }
        return employeeDTO;
    }

    public static Employee mapToEntityWithSupervisor(EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        if (employeeDTO.getManager() != null) {
            employee.setManager(EmployeeMapper.mapToEntity(employeeDTO.getManager()));
        }
        return employee;
    }
}
