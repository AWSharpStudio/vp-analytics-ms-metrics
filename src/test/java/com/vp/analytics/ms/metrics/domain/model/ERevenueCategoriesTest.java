package com.vp.analytics.ms.metrics.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ERevenueCategoriesTest {

    @Test
    void shouldIdentifyByLabel() {
        assertEquals(ERevenueCategories.PROJECT_SALE, ERevenueCategories.labelOf("Venda de Projeto"));
        assertEquals(ERevenueCategories.INSTALLMENT, ERevenueCategories.labelOf("Parcela"));
        assertEquals(ERevenueCategories.UPSELL, ERevenueCategories.labelOf("Upsell"));
        assertEquals(ERevenueCategories.REFERRAL, ERevenueCategories.labelOf("Indicação"));
        assertEquals(ERevenueCategories.NOT_CATEGORIZED, ERevenueCategories.labelOf("Qualquer outro"));

        assertEquals(ERevenueCategories.NOT_CATEGORIZED, ERevenueCategories.labelOf("indicacao"));
        assertEquals(ERevenueCategories.NOT_CATEGORIZED, ERevenueCategories.labelOf("parcela"));
    }
}
