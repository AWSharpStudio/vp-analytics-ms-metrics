package com.vp.analytics.ms.metrics.config;

import com.vp.analytics.ms.metrics.adapters.output.notification.EmailNotificationAdapter;
import com.vp.analytics.ms.metrics.domain.ports.input.CalculateMetricsInputPort;
import com.vp.analytics.ms.metrics.domain.ports.output.MetricsRepositoryPort;
import com.vp.analytics.ms.metrics.domain.ports.output.NotificationPort;
import com.vp.analytics.ms.metrics.domain.ports.output.TransactionQueryPort;
import com.vp.analytics.ms.metrics.domain.usecase.CalculateMetricsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public CalculateMetricsInputPort calculateMetricsInpurPort(
            final TransactionQueryPort queryPort,
            final MetricsRepositoryPort repositoryPort,
            final NotificationPort notificationPort) {
        return new CalculateMetricsUseCase(queryPort, repositoryPort, notificationPort);
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean
    public NotificationPort notificationPort(final JavaMailSender mailSender) {
        return new EmailNotificationAdapter(mailSender);
    }
}
