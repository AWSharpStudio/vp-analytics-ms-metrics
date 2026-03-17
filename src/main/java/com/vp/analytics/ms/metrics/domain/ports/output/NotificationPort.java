package com.vp.analytics.ms.metrics.domain.ports.output;

import java.time.LocalDate;

public interface NotificationPort {
    void sendReport(String clientId, LocalDate referenceDate);
}
