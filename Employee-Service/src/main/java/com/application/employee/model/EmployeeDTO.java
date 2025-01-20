package com.application.employee.model;

import org.springframework.lang.NonNull;

public class EmployeeDTO {

    private String employeeName;
    private String address;
    private String email;
    private Long contactNumber;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String employeeName, String address, String email, Long contactNumber) {
        this.employeeName = employeeName;
        this.address = address;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }
}
