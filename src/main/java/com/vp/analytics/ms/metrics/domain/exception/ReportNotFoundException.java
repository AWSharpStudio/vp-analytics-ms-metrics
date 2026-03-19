package com.vp.analytics.ms.metrics.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(String clientId, LocalDate referenceDate) {
        super("KpiReport não encontrado para cliente=%s periodo=%s".formatted(clientId, referenceDate));
    }
}
