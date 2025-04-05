package com.ecristobale.companies_crud.controllers;

import com.ecristobale.companies_crud.entities.Company;
import com.ecristobale.companies_crud.services.CompanyService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(path = "company")
@Slf4j
@Tag(name = "Companies resource", description = "CRUD operations for companies")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Get a company given a company name")
    @GetMapping(path = "{name}")
    @Observed(name = "company.name")
    @Timed(value = "company.name")
    public ResponseEntity<Company> get(@PathVariable String name) {
        log.info("GET: company {}", name);
//        Force timeout for testing circuit breaker
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return ResponseEntity.ok(this.companyService.readByName(name));
    }

    @Operation(summary = "Save in DB a company given a company body")
    @PostMapping
    @Observed(name = "company.save")
    @Timed(value = "company.save")
    public ResponseEntity<Company> post(@RequestBody Company company) {
        log.info("POST: company {}", company.getName());
        return ResponseEntity.created(
                URI.create(this.companyService.create(company).getName()))
                .build();
    }

    @Operation(summary = "Update in DB a company given a company body and company name")
    @PutMapping(path = "{name}")
    public ResponseEntity<Company> put(@RequestBody Company company,
                                       @PathVariable String name) {
        log.info("PUT: company {}", name);
        return ResponseEntity.ok(this.companyService.update(company, name));
    }

    @Operation(summary = "Delete a company given a company name")
    @DeleteMapping(path = "{name}")
    public ResponseEntity<Company> delete(@PathVariable String name) {
        log.info("DELETE: company {}", name);
        this.companyService.delete(name);
        return ResponseEntity.noContent().build();
    }
}
