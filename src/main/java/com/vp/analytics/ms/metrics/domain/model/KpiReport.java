package com.vp.analytics.ms.metrics.domain.model;

import java.time.LocalDateTime;

public record KpiReport(
        String id,
        String clientId,
        String uploadId,
        LocalDateTime calculatedAt,
        KpiResult result,
        int TransactionCount
) {
}
