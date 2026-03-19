package com.vp.analytics.ms.metrics.adapters.output.persistence;

import com.vp.analytics.ms.metrics.domain.model.KpiReport;
import com.vp.analytics.ms.metrics.domain.ports.input.MetricsRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class JpaMetricsRepositoryAdapter implements MetricsRepositoryPort {

    @Override
    public KpiReport save(KpiReport report) {
        return null;
    }

    @Override
    public Optional<KpiReport> findByClientIdAndPeriod(String clientId, LocalDate referenceDate) {
        return Optional.empty();
    }

    @Override
    public Optional<KpiReport> findByUploadId(String uploadId) {
        return Optional.empty();
    }
}
