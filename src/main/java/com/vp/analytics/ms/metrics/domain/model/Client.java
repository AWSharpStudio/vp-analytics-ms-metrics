package com.vp.analytics.ms.metrics.domain.model;

import java.time.LocalDateTime;

public record Client(
        String clientId,
        String name,
        String email,
        LocalDateTime createdAt
) {
}
