package com.ecristobale.report_ms.helpers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReportHelper {

    @Value("${report.template}")
    private String reportTemplate;

    public String readTemplate() {
        System.out.println("Reading template from " + this.reportTemplate);
        //log.info("Reading template from {}", reportTemplate);
        return "";
    }
}
