package com.vp.analytics.ms.metrics.domain.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ETransactionTypeTest {

    @Test
    void shouldTestIsRevenueAndIsExpense() {
        assertTrue(ETransactionType.REVENUE.isRevenue());
        assertFalse(ETransactionType.EXPENSE.isRevenue());
        assertFalse(ETransactionType.NOT_CATEGORIZED.isRevenue());

        assertTrue(ETransactionType.EXPENSE.isExpense());
        assertFalse(ETransactionType.REVENUE.isExpense());
        assertFalse(ETransactionType.NOT_CATEGORIZED.isExpense());
    }

    @Test
    void shouldTestLabelOf() {
        assertEquals(ETransactionType.REVENUE, ETransactionType.labelOf("Receita"));
        assertEquals(ETransactionType.EXPENSE, ETransactionType.labelOf("Despesa"));
        assertEquals(ETransactionType.NOT_CATEGORIZED, ETransactionType.labelOf("receita"));
        assertEquals(ETransactionType.NOT_CATEGORIZED, ETransactionType.labelOf("Não Categorizado"));
        assertEquals(ETransactionType.NOT_CATEGORIZED, ETransactionType.labelOf("Gastos"));
        assertEquals(ETransactionType.NOT_CATEGORIZED, ETransactionType.labelOf("Ganhos"));
    }
}
