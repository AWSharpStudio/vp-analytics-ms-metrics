package com.vp.analytics.ms.metrics.domain.usecase;

import com.vp.analytics.ms.metrics.domain.calculation.KpiData;
import com.vp.analytics.ms.metrics.domain.model.KpiReport;
import com.vp.analytics.ms.metrics.domain.model.KpiResult;
import com.vp.analytics.ms.metrics.domain.model.TransactionIngestedEvent;
import com.vp.analytics.ms.metrics.domain.ports.output.MetricsRepositoryPort;
import com.vp.analytics.ms.metrics.domain.ports.output.NotificationPort;
import com.vp.analytics.ms.metrics.domain.ports.output.TransactionQueryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateMetricsUseCaseTest {

    @Mock
    private TransactionQueryPort queryPort;

    @Mock
    private MetricsRepositoryPort metricsRepository;

    @Mock
    private NotificationPort notificationPort;

    private CalculateMetricsUseCase useCase;

    private static final String CLIENT_ID = "clientId";
    private static final TransactionIngestedEvent EVENT = new TransactionIngestedEvent(
            "uploadId", CLIENT_ID, 4, List.of());

    @BeforeEach
    void setup() {
        useCase = new CalculateMetricsUseCase(queryPort, metricsRepository, notificationPort);
        when(metricsRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void shouldCallFetchKpiDataSuccessfully() {
        when(queryPort.fetchKpiData(eq(CLIENT_ID), any())).thenReturn(emptyKpiData());
        useCase.calculate(EVENT);
        verify(queryPort, times(1)).fetchKpiData(eq(CLIENT_ID), any(LocalDate.class));
        verify(metricsRepository, times(1)).save(any(KpiReport.class));
        verify(notificationPort, times(1)).sendReport(eq(CLIENT_ID), any(LocalDate.class), any(KpiReport.class));
    }

    @Test
    void shouldBuildCorrectKpiResult() {
        when(queryPort.fetchKpiData(any(), any())).thenReturn(new KpiData(
                CLIENT_ID,
                LocalDate.of(2026, 1, 1),
                new BigDecimal("25000"), // totalRevenue
                new BigDecimal("10000"), // totalExpenses
                new BigDecimal("5000"), // totalAcquisitionExpenses
                5,
                2,
                Map.of(),
                Map.of()
        ));

        useCase.calculate(EVENT);

        ArgumentCaptor<KpiReport> captor = ArgumentCaptor.forClass(KpiReport.class);
        verify(queryPort, times(1)).fetchKpiData(eq(CLIENT_ID), any(LocalDate.class));
        verify(metricsRepository, times(1)).save(captor.capture());
        verify(notificationPort, times(1)).sendReport(eq(CLIENT_ID), any(LocalDate.class), any(KpiReport.class));

        KpiResult result = captor.getValue().result();

        assertEquals(new BigDecimal("5000.00"), result.averageTicket());
        assertEquals(new BigDecimal("2500.00"), result.cac());
        assertEquals(new BigDecimal("60000.00"), result.ltv());
        assertEquals(new BigDecimal("15000"), result.netResult());
    }

    @Test
    void shouldPersistReportWithCorrectMetadata() {
        when(queryPort.fetchKpiData(eq(CLIENT_ID), any())).thenReturn(emptyKpiData());

        useCase.calculate(EVENT);

        ArgumentCaptor<KpiReport> captor = ArgumentCaptor.forClass(KpiReport.class);
        verify(queryPort, times(1)).fetchKpiData(eq(CLIENT_ID), any(LocalDate.class));
        verify(metricsRepository, times(1)).save(captor.capture());
        verify(notificationPort, times(1)).sendReport(eq(CLIENT_ID), any(LocalDate.class), any(KpiReport.class));


        assertEquals(CLIENT_ID, captor.getValue().clientId());
        assertEquals("uploadId", captor.getValue().uploadId());
        assertEquals(4, captor.getValue().transactionCount());
    }

    private KpiData emptyKpiData() {
        return new KpiData(
                CLIENT_ID,
                LocalDate.of(2026, 1, 1),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                0,
                0,
                Map.of(),
                Map.of()
        );
    }
}
