package com.ecristobale.report_ms.services;

import com.ecristobale.report_ms.helpers.ReportHelper;
import com.ecristobale.report_ms.models.Company;
import com.ecristobale.report_ms.models.WebSite;
import com.ecristobale.report_ms.repositories.CompaniesFallbackRepository;
import com.ecristobale.report_ms.repositories.CompaniesRepository;
import com.ecristobale.report_ms.streams.ReportPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final CompaniesRepository companiesRepository;
    private final CompaniesFallbackRepository companiesFallbackRepository;
    private final ReportHelper reportHelper;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private final ReportPublisher reportPublisher;
    private final ObjectMapper objectMapper;

    @Override
    public String makeReport(String name) {
        var circuitBreaker = this.circuitBreakerFactory.create("companies-circuitbreaker");
        return circuitBreaker.run(
                () -> this.makeReportMain(name),
                throwable -> this.makeReportFallback(name, throwable)
        );
    }

    @Override
    public String saveReport(String report) {
        var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var placeholders = this.reportHelper.getPlaceHoldersFromTemplate(report);
        var circuitBreaker = this.circuitBreakerFactory.create("companies-circuitbreaker-event");

        var webSites = Stream.of(placeholders.get(3))
                .map(webSite -> WebSite.builder().name(webSite).build())
                .toList();

        var company = Company.builder()
                .name(placeholders.get(0))
                .foundationDate(LocalDate.parse(placeholders.get(1), format))
                .founder(placeholders.get(2))
                .webSites(webSites)
                .build();

        this.reportPublisher.publishReport(report);

        circuitBreaker.run(
                () -> this.companiesRepository.postByName(company),
                throwable -> this.reportPublisher.publishCbReport(this.buildEventMsg(company))
        );

        return "Saved";
    }

    @Override
    public void deleteReport(String name) {
        this.companiesRepository.deleteByName(name);
    }

    private String makeReportMain(String name) {
        return this.reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
    }

    private String makeReportFallback(String name, Throwable error) {
        log.warn("Error: {}", error.getMessage());
        return this.reportHelper.readTemplate(this.companiesFallbackRepository.getByName(name));
    }

    private String buildEventMsg(Company company) {
        try {
            return this.objectMapper.writeValueAsString(company);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
