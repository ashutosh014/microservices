package com.application.employee.service;

import com.application.employee.entity.Employee;
import com.application.employee.model.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    public Employee createEmployee(EmployeeDTO employeeDTO);

    public List<EmployeeDTO> getAllEmployees();

    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO);

    public Employee updateEmployeeContact(Long id, long contactNumber);

    public void deleteEmployee(Long id);
}
