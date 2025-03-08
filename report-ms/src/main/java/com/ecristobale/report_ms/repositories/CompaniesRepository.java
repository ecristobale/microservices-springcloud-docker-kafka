package com.ecristobale.report_ms.repositories;

import com.ecristobale.report_ms.beans.LoadBalancerConfiguration;
import com.ecristobale.report_ms.models.Company;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "companies-crud")
@LoadBalancerClient(name = "companies-crud", configuration = LoadBalancerConfiguration.class)
public interface CompaniesRepository {

    @GetMapping("/companies-crud/company/{name}")
    Optional<Company> getByName(@PathVariable String name);

    @PostMapping("/companies-crud/company")
    Optional<Company> postByName(@RequestBody Company company);

    @DeleteMapping("/companies-crud/company/{name}")
    void deleteByName(@PathVariable String name);
}
