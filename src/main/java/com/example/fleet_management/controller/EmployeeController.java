package com.example.fleet_management.controller;

import com.example.fleet_management.entity.Employee;
import com.example.fleet_management.entity.Matatu;
import com.example.fleet_management.repositories.EmployeeRepository;
import com.example.fleet_management.response.ApiResponse;
import com.example.fleet_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('marshall')")
    @GetMapping("/drivers")
    public List<Employee> getAllDrivers(){
        return employeeService.getAllDrivers();
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('marshall')")
    @GetMapping("/marshalls")
    public List<Employee> getAllMarshalls(){
        return employeeService.getAllMarshalls();
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/admins")
    public List<Employee> getAllAdmins(){
        return employeeService.getAllAdmins();
    }

    //Get one employee details
    @PreAuthorize("hasAuthority('admin') or hasAuthority('marshall')")
    @GetMapping("{email}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeDetails(@PathVariable String email){
        Optional<Employee> employee = employeeService.getEmployeeDetails(email);

        if(employee.isPresent()){
            ApiResponse<Employee> response = new ApiResponse<>(1, "Employee fetched successfully", employee.get());
            return ResponseEntity.ok(response);
        }else {
            ApiResponse<Employee> response = new ApiResponse<>(0, "Employee not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee){
        try {
            Employee createdEmployee = employeeService.createEmployee(employee);
            ApiResponse<Employee> response = new ApiResponse<>(1, "Employee added successfully", createdEmployee);
            return ResponseEntity.ok(response);
        }catch(IllegalArgumentException e){
            ApiResponse<Employee> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("{email}")
    public ResponseEntity<ApiResponse<Employee>> deleteEmployee(@PathVariable String email){
        Optional<Employee> existingEmployee = employeeService.getEmployeeDetails(email);
        if(existingEmployee.isEmpty()){
            ApiResponse<Employee> response = new ApiResponse<>(0, "Employee not found", null);
            return ResponseEntity.status(404).body(response);
        }

        employeeService.deleteEmployee(email);
        ApiResponse<Employee> response = new ApiResponse<>(1, "Employee deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
