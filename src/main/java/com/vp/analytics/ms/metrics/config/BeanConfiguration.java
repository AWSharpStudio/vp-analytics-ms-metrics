package com.vp.analytics.ms.metrics.config;

import com.vp.analytics.ms.metrics.adapters.output.notification.EmailNotificationAdapter;
import com.vp.analytics.ms.metrics.domain.ports.output.NotificationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class BeanConfiguration {

    @Bean
    public NotificationPort notificationPort(
            final JavaMailSender mailSender
    ) {
        return new EmailNotificationAdapter(mailSender);
    }
}
