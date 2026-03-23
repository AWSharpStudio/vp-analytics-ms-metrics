package com.vp.analytics.ms.metrics.adapters.output.notification;

import com.vp.analytics.ms.metrics.domain.exception.ReportNotFoundException;
import com.vp.analytics.ms.metrics.domain.model.KpiReport;
import com.vp.analytics.ms.metrics.domain.model.KpiResult;
import com.vp.analytics.ms.metrics.domain.ports.input.MetricsRepositoryPort;
import com.vp.analytics.ms.metrics.domain.ports.output.NotificationPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

public class EmailNotificationAdapter implements NotificationPort {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationAdapter.class);

    private final JavaMailSender mailSender;

    private final MetricsRepositoryPort metricsRepository;

    public EmailNotificationAdapter(JavaMailSender mailSender,
                                    MetricsRepositoryPort metricsRepository) {
        this.mailSender = mailSender;
        this.metricsRepository = metricsRepository;
    }

    @Value("${notification.email.from:noreply@awsharpstudio.com}")
    private String emailFrom;

    @Override
    public void sendReport(String clientId, LocalDate referenceDate) {
        Optional<KpiReport> reportOptional = metricsRepository.findByClientIdAndPeriod(clientId, referenceDate);

        if (reportOptional.isEmpty()) {
            log.warn("No KpiReport found. clientId={} period={}", clientId, referenceDate);
            throw new ReportNotFoundException(clientId, referenceDate);
        }

        KpiReport report = reportOptional.get();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            ClassPathResource logo = new ClassPathResource("static/logo-colorida.png");

            helper.setTo(report.clientEmail());
            helper.setFrom(emailFrom);
            helper.setSubject(referenceDate == null
                    ? "VP Analytics | KPIs Upload " + report.uploadId()
                    : String.format("VP Analytics | KPIs %02d/%d", referenceDate.getMonthValue(), referenceDate.getYear()));
            helper.setText(buildHtmlBody(report), true);
            helper.addInline("company-logo", logo);
            mailSender.send(message);
            log.info("Report sent clientId={} period={}", clientId, referenceDate);
        } catch (MessagingException e) {
            log.error("Failed to send report clientId={} period={}", clientId, referenceDate, e);
        }
    }


    private String buildHtmlBody(KpiReport report) {
        KpiResult result = report.result();

        StringBuilder revenueRows = new StringBuilder();
        result.revenueByCategory().forEach((category, value) ->
                revenueRows.append("<tr><td>").append(category).append("</td>")
                        .append("<td>R$ ").append(value).append("</td></tr>")
        );

        StringBuilder expenseRows = new StringBuilder();
        result.expenseByCategory().forEach((category, value) ->
                expenseRows.append("<tr><td>").append(category).append("</td>")
                        .append("<td>R$ ").append(value).append("</td></tr>")
        );

        return """
                <html>
                <body style="font-family: Arial, sans-serif; color: #2C3E50;">
                  <h2>Relatório de KPIs — VendaPlanejada Analytics</h2>
                  <p><b>Upload ID:</b> %s</p>
                  <p><b>Período de referência:</b> %s</p>
                  <p><b>Transações processadas:</b> %d</p>
                  <hr/>
                
                  <h3>Indicadores do Período</h3>
                  <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse;">
                    <tr style="background-color: #1E3A5F; color: white;">
                      <th>KPI</th><th>Valor</th>
                    </tr>
                    <tr><td>Ticket Médio</td><td>R$ %s</td></tr>
                    <tr><td>CAC</td><td>R$ %s</td></tr>
                    <tr><td>LTV</td><td>R$ %s</td></tr>
                    <tr><td>Resultado Líquido</td><td>R$ %s</td></tr>
                  </table>
                
                  <h3>Receitas por Categoria</h3>
                  <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse;">
                    <tr style="background-color: #1E3A5F; color: white;">
                      <th>Categoria</th><th>Total</th>
                    </tr>
                    %s
                  </table>
                
                  <h3>Despesas por Categoria</h3>
                  <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse;">
                    <tr style="background-color: #1E3A5F; color: white;">
                      <th>Categoria</th><th>Total</th>
                    </tr>
                    %s
                  </table>
                
                  <br/>
                  <table cellpadding="0" cellspacing="0" width="100%%"
                         style="margin-top:24px; border-top:2px solid #1E3A5F; padding-top:16px;
                                font-family:Arial,sans-serif; font-size:13px; color:#2C3E50;">
                    <tr valign="top">
                
                      <!-- coluna esquerda: logo -->
                      <td width="64" style="padding-right:16px;">
                        <img src="cid:company-logo" alt="VendaPlanejada"
                             height="48" style="display:block;"/>
                      </td>
                
                      <td width="1" style="background-color:#1E3A5F;">&nbsp;</td>
                
                      <td style="padding-left:16px; line-height:1.7;">
                        <span style="font-size:15px; font-weight:bold; color:#1E3A5F;">%s</span><br/>
                        <span style="color:#7F8C8D; font-size:12px; text-transform:uppercase;
                                     letter-spacing:0.5px;">%s</span><br/>
                        <a href="mailto:%s"
                           style="color:#1E3A5F; text-decoration:none; font-size:13px;">%s</a>
                      </td>
                
                      <td align="right" valign="middle"
                          style="color:#BDC3C7; font-size:11px; padding-left:24px;">
                        Gerado por <b style="color:#1E3A5F;">VendaPlanejada Analytics</b><br/>
                        <span style="font-size:10px;">%s</span>
                      </td>
                
                    </tr>
                  </table>
                </body>
                </html>
                """.formatted(
                report.uploadId(),
                report.referenceDate() != null
                        ? report.referenceDate().getMonth().getDisplayName(
                        TextStyle.FULL, new Locale("pt", "BR"))
                        + "/" + report.referenceDate().getYear()
                        : "Upload pontual",
                report.transactionCount(),
                result.averageTicket(),
                result.cac(),
                result.ltv(),
                result.netResult(),
                revenueRows,
                expenseRows,
                "Abner Werley Silva",
                "Sócio-Fundador",
                "awsharpstudio@gmail.com",
                "awsharpstudio@gmail.com",
                LocalDate.now()
        );
    }
}
