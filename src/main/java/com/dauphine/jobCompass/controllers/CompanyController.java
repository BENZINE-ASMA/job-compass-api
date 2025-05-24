package com.dauphine.jobCompass.controllers;

import com.dauphine.jobCompass.dto.Company.CompanyCreationRequest;
import com.dauphine.jobCompass.model.Company;
import com.dauphine.jobCompass.model.JobCategory;
import com.dauphine.jobCompass.services.Company.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }
    @Operation(summary = "Get all companies")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved companies")
    })
    @GetMapping("/Companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(this.companyService.getAllCompanies());
    }
    @Operation(summary = "Get all companies name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved companies name")
    })
    @GetMapping("/Companies/Names")
    public ResponseEntity<List<String>> getAllCompaniesNames() {
        return ResponseEntity.ok(this.companyService.getAllCompaniesNames());
    }
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CompanyCreationRequest request) {
        Company company = companyService.createCompany(request);
        return ResponseEntity.created(URI.create("/api/companies/" + company.getId()))
                .body(company);
    }
}
