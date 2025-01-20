package com.application.employee.controller;

import com.application.employee.entity.Employee;
import com.application.employee.model.EmployeeDTO;
import com.application.employee.service.EmployeeServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceIMPL employeeService;

    public EmployeeController(EmployeeServiceIMPL employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = employeeService.createEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee Created Successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create employee");
        }

    }

    @GetMapping("/getAll")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,
                                            @RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = employeeService.updateEmployee(id,employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee Updated Successfully");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with id: " + id);
        }
    }

    @PatchMapping("/update/contact/{id}")
    public ResponseEntity<?> updateEmployeeContact(@PathVariable Long id,
                                                @RequestParam Long contactNumber) {
        try {
            Employee employee = employeeService.updateEmployeeContact(id,contactNumber);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee Updated Successfully");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee Deleted Successfully");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with id: " + id);
        }
    }
}
