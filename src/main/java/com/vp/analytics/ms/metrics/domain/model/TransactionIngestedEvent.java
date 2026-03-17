package com.vp.analytics.ms.metrics.domain.model;

import java.util.List;

public record TransactionIngestedEvent(
        String uploadId,
        int transactionCount,
        List<Transaction> transactions) {
}
