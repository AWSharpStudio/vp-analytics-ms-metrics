package com.vp.analytics.ms.metrics.domain.model;

import lombok.Getter;

@Getter
public enum EExpenseCategories {
    PAID_TRAFFIC("Tráfego Pago", true),
    SALES_TEAM("Equipe de Vendas", true),
    MARKETING_TEAM("Equipe de Marketing", true),
    PRO_LABORE("Pró-labore", false),
    TAXES("Impostos", false),
    NOT_CATEGORIZED("Não Categorizado", false);

    private final String label;
    private final boolean isAcquisitionCost;

    EExpenseCategories(final String label, final boolean isAcquisitionCost) {
        this.label = label;
        this.isAcquisitionCost = isAcquisitionCost;
    }

    public static EExpenseCategories labelOf(final String label) {
        for (final EExpenseCategories value : values()) {
            if (value.label.equals(label)) {
                return value;
            }
        }
        return NOT_CATEGORIZED;
    }
}