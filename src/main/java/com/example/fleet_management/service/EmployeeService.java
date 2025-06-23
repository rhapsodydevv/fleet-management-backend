package com.example.fleet_management.service;

import com.example.fleet_management.entity.Employee;
import com.example.fleet_management.entity.Matatu;
import com.example.fleet_management.repositories.EmployeeRepository;
import com.example.fleet_management.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public List<Employee> getAllDrivers(){
        return employeeRepository.findByRole("driver");
    }

    public List<Employee> getAllMarshalls(){
        return employeeRepository.findByRole("marshall");
    }

    public List<Employee> getAllAdmins(){
        return employeeRepository.findByRole("admin");
    }

    public Optional<Employee> getEmployeeDetails(String email){
        return employeeRepository.findById(email);
    }

    public Employee createEmployee(Employee employee){
        String email = employee.getEmail();
        email = email.replaceAll("\\s", "".toLowerCase());
        employee.setEmail(email);
        Optional<Employee> existing = employeeRepository.findById(email);

        if(email.isBlank()){
            throw new IllegalArgumentException("Email field cannot be empty");
        }
        if(existing.isPresent()){
            throw new IllegalArgumentException("Employee already exists with email: " + email);        }

        return  employeeRepository.save(employee);
    }

//    public String createEmployee(Employee employee){
//        String email = employee.getEmail();
//        if(email.isBlank()||email==null){
//            return "Email field cannot be empty";
//        }
//
//        Optional<Employee> existing = employeeRepository.findById(email);
//        if(existing.isPresent()){
//            return "User " + email + " already exists";
//        }
//        employeeRepository.save(employee);
//        return email + " created successfully";
//    }

    public Employee deleteEmployee(String email){
        email = email.replaceAll("\\s", "".toLowerCase());
        Optional<Employee> existing = employeeRepository.findById(email);

        if (existing.isEmpty()){
            throw new IllegalArgumentException("Employee with email " + email + " does not exist");
        }
        employeeRepository.deleteById(email);

        return existing.get();
    }
}
