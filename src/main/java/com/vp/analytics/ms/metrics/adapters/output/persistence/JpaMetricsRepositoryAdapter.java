package com.vp.analytics.ms.metrics.adapters.output.persistence;

import com.vp.analytics.ms.metrics.domain.model.KpiReport;
import com.vp.analytics.ms.metrics.domain.ports.output.MetricsRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class JpaMetricsRepositoryAdapter implements MetricsRepositoryPort {

    @Override
    public KpiReport save(final KpiReport report) {
        return null;
    }

    @Override
    public Optional<KpiReport> findByClientIdAndPeriod(final String clientId, final LocalDate referenceDate) {
        return Optional.empty();
    }

    @Override
    public Optional<KpiReport> findByUploadId(final String uploadId) {
        return Optional.empty();
    }
}
