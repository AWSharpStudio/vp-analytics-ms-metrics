package com.vp.analytics.ms.metrics.domain.model;

import java.util.List;

public record TransactionIngestedEvent(
        String uploadId,
        String clientId,
        int transactionCount,
        List<Transaction> transactions) {
}
