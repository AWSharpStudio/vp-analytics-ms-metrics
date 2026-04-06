package com.vp.analytics.ms.metrics.adapters.output.notification;

import com.vp.analytics.ms.metrics.domain.model.EExpenseCategories;
import com.vp.analytics.ms.metrics.domain.model.ERevenueCategories;
import com.vp.analytics.ms.metrics.domain.model.KpiReport;
import com.vp.analytics.ms.metrics.domain.model.KpiResult;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailNotificationAdapterTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailNotificationAdapter notificationAdapter;

    public static final String CLIENT_ID = "any-client-id";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(notificationAdapter, "emailFrom", "noreply@awsharpstudio.com");
    }

    @Test
    void shouldSendRecentUploadReport() throws MessagingException {
        KpiReport report = buildReport(null);
        MimeMessage realMessage = new MimeMessage((Session) null);

        when(mailSender.createMimeMessage()).thenReturn(realMessage);

        assertDoesNotThrow(() -> notificationAdapter.sendReport(CLIENT_ID, null, report));

        ArgumentCaptor<MimeMessage> messageCaptor = ArgumentCaptor.forClass(MimeMessage.class);

        verify(mailSender).send(messageCaptor.capture());
        MimeMessage sentMessage = messageCaptor.getValue();


        assertEquals("VP Analytics | KPIs Upload " + report.uploadId(), sentMessage.getSubject());
        assertEquals("marcenarialegal@gmail.com", sentMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
        assertEquals("noreply@awsharpstudio.com", sentMessage.getFrom()[0].toString());
    }

    @Test
    void shouldSendUploadReportPerReferenceDate() throws MessagingException {
        LocalDate referenceDate = LocalDate.of(2026, 3, 10);
        KpiReport report = buildReport(referenceDate);
        MimeMessage realMessage = new MimeMessage((Session) null);

        when(mailSender.createMimeMessage()).thenReturn(realMessage);

        assertDoesNotThrow(() -> notificationAdapter.sendReport(CLIENT_ID, referenceDate, report));

        ArgumentCaptor<MimeMessage> messageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(messageCaptor.capture());
        MimeMessage sentMessage = messageCaptor.getValue();


        assertEquals("VP Analytics | KPIs 03/2026", sentMessage.getSubject());
        assertEquals("marcenarialegal@gmail.com", sentMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
        assertEquals("noreply@awsharpstudio.com", sentMessage.getFrom()[0].toString());
    }

    @Test
    void shouldNotThrowWhenSmtpFails() {
        KpiReport report = buildReport(null);

        MimeMessage message = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(message);
        try (MockedConstruction<MimeMessageHelper> helperMock = mockConstruction(MimeMessageHelper.class,
                (mock, context) ->
                        doThrow(new MessagingException("error")).when(mock).setTo(anyString()))) {
            assertDoesNotThrow(() -> notificationAdapter.sendReport(CLIENT_ID, null, report));

            verify(mailSender, never()).send(any(MimeMessage.class));
        }
    }


    private KpiReport buildReport(LocalDate referenceDate) {
        KpiResult kpiResult = new KpiResult(
                new BigDecimal("200.00"),
                new BigDecimal("200.00"),
                new BigDecimal("200.00"),
                new BigDecimal("200.00"),
                Map.of(ERevenueCategories.PROJECT_SALE, new BigDecimal("200.00")),
                Map.of(EExpenseCategories.MARKETING_TEAM, new BigDecimal("200.00")));
        return new KpiReport("id", CLIENT_ID, "uploadId", "marcenarialegal@gmail.com",
                LocalDateTime.now(), kpiResult, 3, referenceDate);
    }
}
