package com.ecristobale.report_ms.services;

import com.ecristobale.report_ms.repositories.CompaniesRepository;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final CompaniesRepository companiesRepository;
    private final EurekaClient eurekaClient;

    @Override
    public String makeReport(String name) {
        return this.companiesRepository.getByName(name).orElseThrow().getName();
    }

    @Override
    public String saveReport(String nameReport) {
        return "Report " + nameReport + " has been saved";
    }

    @Override
    public void deleteReport(String name) {
        System.out.println("Report " + name + " has been deleted");
    }
}
