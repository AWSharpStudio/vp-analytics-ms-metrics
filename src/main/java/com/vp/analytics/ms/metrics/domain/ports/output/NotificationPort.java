package com.vp.analytics.ms.metrics.domain.ports.output;

import com.vp.analytics.ms.metrics.domain.model.KpiReport;

import java.time.LocalDate;

/**
 * Output port to send KPI reports to the client via given notification channel
 *
 * <p>
 * This pure Java interface declares what the application needs from external systems.
 * Abstracting the implementation and remaining framework-free ensures easy technology swapping and channel expansion
 */
public interface NotificationPort {
    void sendReport(String clientId, LocalDate referenceDate, KpiReport report);
}
