package com.vp.analytics.ms.metrics.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record KpiReport(
        String id,
        String clientId,
        String uploadId,
        String clientEmail,
        LocalDateTime calculatedAt,
        KpiResult result,
        int transactionCount,
        LocalDate referenceDate
) {
    public KpiReport(final String id,
                     final String clientId,
                     final String uploadId,
                     final String clientEmail,
                     final LocalDateTime calculatedAt,
                     final KpiResult result,
                     final int transactionCount,
                     final LocalDate referenceDate) {
        this.id = id;
        this.clientId = clientId;
        this.uploadId = uploadId;
        this.clientEmail = clientEmail;
        this.calculatedAt = calculatedAt;
        this.result = result;
        this.transactionCount = transactionCount;
        this.referenceDate = referenceDate;
    }
}
