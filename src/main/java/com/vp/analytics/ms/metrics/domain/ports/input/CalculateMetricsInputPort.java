package com.vp.analytics.ms.metrics.domain.ports.input;

import com.vp.analytics.ms.metrics.domain.model.TransactionIngestedEvent;

public interface CalculateMetricsInputPort {
    void calculate(TransactionIngestedEvent event);
}
