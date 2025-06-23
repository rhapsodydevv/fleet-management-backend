package com.example.fleet_management.controller;

import com.example.fleet_management.entity.Matatu;
import com.example.fleet_management.response.ApiResponse;
import com.example.fleet_management.service.MatatuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matatus")
public class MatatuController{
    @Autowired
    private MatatuService matatuService;

    @PreAuthorize("hasAuthority('admin') or hasAuthority('marshall')")
    @GetMapping
    public List<Matatu> getAllMatatus(){
        return matatuService.getAllMatatus();
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('marshall')")
    @GetMapping("{plateNumber}")
    public ResponseEntity<ApiResponse<Matatu>> getMatatuDetails(@PathVariable String plateNumber){
        Optional<Matatu> matatu = matatuService.getMatatuDetails(plateNumber);

        if(matatu.isPresent()){
            ApiResponse<Matatu> response = new ApiResponse<>(1, "Matatu fetched successfully", matatu.get());
            return ResponseEntity.ok(response);
        }else {
            ApiResponse<Matatu> response = new ApiResponse<>(0, "Matatu not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }


    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<ApiResponse<Matatu>> createMatatu(@RequestBody Matatu matatu){
        try {
            Matatu createdMatatu = matatuService.createMatatu(matatu);
            ApiResponse<Matatu> response = new ApiResponse<>(1, "Matatu created successfully", createdMatatu);
            return ResponseEntity.ok(response);
        }catch(IllegalArgumentException e){
            ApiResponse<Matatu> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("{plateNumber}")
    public ResponseEntity<ApiResponse<Matatu>> deleteMatatu(@PathVariable String plateNumber){
        Optional<Matatu> existingMatatu = matatuService.getMatatuDetails(plateNumber);
        if(existingMatatu.isEmpty()){
            ApiResponse<Matatu> response = new ApiResponse<>(0, "Matatu not found", null);
            return ResponseEntity.status(404).body(response);
        }

        matatuService.deleteMatatu(plateNumber);
        ApiResponse<Matatu> response = new ApiResponse<>(1, "Matatu deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('marshall')")
    @PutMapping("{plateNumber}/assign-driver/{email}")
    public String assignDriver(@PathVariable String plateNumber, @PathVariable String email){
        return matatuService.assignDriverToMatatu(plateNumber, email);
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('marshall')")
    @PutMapping("{plateNumber}/assign-status/{status}")
    public String assignStatus(@PathVariable String plateNumber, @PathVariable String status){
        return matatuService.assignStatus(plateNumber, status);
    }

    //assign marshall to stage
}
