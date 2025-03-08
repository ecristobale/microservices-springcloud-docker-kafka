package com.ecristobale.report_ms.services;

import com.ecristobale.report_ms.helpers.ReportHelper;
import com.ecristobale.report_ms.models.Company;
import com.ecristobale.report_ms.repositories.CompaniesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final CompaniesRepository companiesRepository;
    private final ReportHelper reportHelper;

    @Override
    public String makeReport(String name) {
        return this.reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
    }

    @Override
    public String saveReport(String nameReport) {
        var company = Company.builder()
                .name("test")
                .logo("logo")
                .founder("test")
                .foundationDate(LocalDate.now())
                .webSites(List.of())
                .build();

        this.companiesRepository.postByName(company);
        return "Report " + nameReport + " has been saved";
    }

    @Override
    public void deleteReport(String name) {
        System.out.println("Report " + name + " has been deleted");
    }
}
