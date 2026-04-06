package com.vp.analytics.ms.metrics.domain.ports.input;

import com.vp.analytics.ms.metrics.domain.model.TransactionIngestedEvent;

/**
 * Input port that triggers the metrics calculation use case.
 * <p>
 * This pure Java interface exposes domain capabilities to external actors.
 * Being framework-free allows the application to be driven by any mechanism,
 * such as message brokers or REST APIs.
 */
public interface CalculateMetricsInputPort {

    /**
     * Orchestrates the fetching of data, calculation of formulas, and persistence of the report.
     *
     * @param event the {@link TransactionIngestedEvent} containing the trigger data.
     */
    void calculate(TransactionIngestedEvent event);
}
