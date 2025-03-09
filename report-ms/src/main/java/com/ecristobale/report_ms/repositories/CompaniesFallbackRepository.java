package com.ecristobale.report_ms.repositories;

import com.ecristobale.report_ms.models.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
@Slf4j
public class CompaniesFallbackRepository {

    private WebClient webClient;
    private final String uri;

    public CompaniesFallbackRepository(@Value("${fallback.uri}") String uri, WebClient webClient) {
        this.uri = uri;
        this.webClient = webClient;
    }

    public Company getByName(String name) {
        log.warn("Calling companies fallback {}", this.uri);
        return this.webClient
                .get()
                .uri(this.uri, name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Company.class)
                .log()
                .block();
    }
}
