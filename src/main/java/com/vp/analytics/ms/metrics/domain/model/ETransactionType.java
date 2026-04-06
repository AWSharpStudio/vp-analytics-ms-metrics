package com.vp.analytics.ms.metrics.domain.model;


import lombok.Getter;

@Getter
public enum ETransactionType {
    REVENUE("Receita"),
    EXPENSE("Despesa"),
    NOT_CATEGORIZED("Não Categorizado");

    private final String label;

    ETransactionType(final String label) {
        this.label = label;
    }

    public boolean isRevenue() {
        return this == REVENUE;
    }

    public boolean isExpense() {
        return this == EXPENSE;
    }

    public static ETransactionType labelOf(final String label) {
        for (final ETransactionType value : values()) {
            if (value.label.equals(label)) {
                return value;
            }
        }
        return NOT_CATEGORIZED;
    }
}