package com.ecristobale.report_ms.streams;

import com.ecristobale.report_ms.models.Company;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReportPublisher {

    private final StreamBridge streamBridge;

    /*
     * Kafka: topic name -> consumerReport
     */
    public void publishReport(String report) {
        streamBridge.send("consumerReport", report);
        streamBridge.send("consumerReport-in-0", report);
        streamBridge.send("consumerReport-out-0", report);
    }

    /*
     * Kafka: topic name -> consumerCbReport
     *
     * This method is on charge of publishing on the broker the company object
     * in case of a circuit breaker fallback.
     */
    public Company publishCbReport(String company) {
        streamBridge.send("consumerCbReport", company);
        streamBridge.send("consumerCbReport-in-0", company);
        streamBridge.send("consumerCbReport-out-0", company);

        return Company.builder().build(); // Dummy return, Cb needs to return an object
    }
}
