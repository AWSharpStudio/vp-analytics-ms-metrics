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
}
