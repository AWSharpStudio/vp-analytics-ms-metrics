package com.vp.analytics.ms.metrics.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EExpenseCategoriesTest {

    @Test
    void shouldIdentifyCategories() {
        assertTrue(EExpenseCategories.SALES_TEAM.isAcquisitionCost());
        assertTrue(EExpenseCategories.PAID_TRAFFIC.isAcquisitionCost());
        assertTrue(EExpenseCategories.MARKETING_TEAM.isAcquisitionCost());
        assertFalse(EExpenseCategories.PRO_LABORE.isAcquisitionCost());
        assertFalse(EExpenseCategories.TAXES.isAcquisitionCost());
        assertFalse(EExpenseCategories.NOT_CATEGORIZED.isAcquisitionCost());
    }

    @Test
    void shouldIdentifyByLabel() {
        assertEquals(EExpenseCategories.PAID_TRAFFIC, EExpenseCategories.labelOf("Tráfego Pago"));
        assertEquals(EExpenseCategories.SALES_TEAM, EExpenseCategories.labelOf("Equipe de Vendas"));
        assertEquals(EExpenseCategories.MARKETING_TEAM, EExpenseCategories.labelOf("Equipe de Marketing"));
        assertEquals(EExpenseCategories.PRO_LABORE, EExpenseCategories.labelOf("Pró-labore"));
        assertEquals(EExpenseCategories.TAXES, EExpenseCategories.labelOf("Impostos"));
        assertEquals(EExpenseCategories.NOT_CATEGORIZED, EExpenseCategories.labelOf("Qualquer outro"));

        assertEquals(EExpenseCategories.NOT_CATEGORIZED, EExpenseCategories.labelOf("Trafego Pago"));
        assertEquals(EExpenseCategories.NOT_CATEGORIZED, EExpenseCategories.labelOf("equipe de vendas"));
    }


}
