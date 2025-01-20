package com.application.employee.service;

import com.application.employee.entity.Employee;
import com.application.employee.model.EmployeeDTO;
import com.application.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceIMPL implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceIMPL(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEmployee(employeeDTO);
        return employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        List<EmployeeDTO> employeeDetails = new ArrayList<>();

        for(Employee employee : employees) {
            EmployeeDTO employeeDTO = convertoEmployeeDTO(employee);
            employeeDetails.add(employeeDTO);
        }

        return employeeDetails;
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isPresent()) {
            Employee updatedEmployee = employee.get();
            updatedEmployee.setAddress(employeeDTO.getAddress());
            updatedEmployee.setEmployeeName(employeeDTO.getEmployeeName());
            updatedEmployee.setEmail(employeeDTO.getEmail());
            updatedEmployee.setContactNumber(employeeDTO.getContactNumber());

            return employeeRepository.save(updatedEmployee);
        }

        throw new IllegalArgumentException("No employee found for Id : " + id);
    }

    @Override
    public Employee updateEmployeeContact(Long id, long contactNumber) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isPresent()) {
            Employee updatedEmployee = employee.get();
            updatedEmployee.setContactNumber(contactNumber);

            return employeeRepository.save(updatedEmployee);
        }

        throw new IllegalArgumentException("No employee found for Id : " + id);
    }

    @Override
    public void deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isPresent()) {
            employeeRepository.save(employee.get());
        }
        throw new IllegalArgumentException("No employee found for Id : " + id);
    }

    private Employee convertToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setContactNumber(employeeDTO.getContactNumber());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAddress(employeeDTO.getAddress());

        return employee;
    }

    private EmployeeDTO convertoEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName(employeeDTO.getEmployeeName());
        employeeDTO.setAddress(employeeDTO.getAddress());
        employeeDTO.setContactNumber(employee.getContactNumber());
        employeeDTO.setEmail(employee.getEmail());

        return employeeDTO;
    }
}
