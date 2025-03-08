package com.ecristobale.report_ms.services;

public interface ReportService {

    String makeReport(String name);
    String saveReport(String nameReport);
    void deleteReport(String name);
}
