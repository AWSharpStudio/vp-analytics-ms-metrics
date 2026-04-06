package com.vp.analytics.ms.metrics.adapters.output.persistence.repository;

import com.vp.analytics.ms.metrics.adapters.output.persistence.TransactionEntity;
import com.vp.analytics.ms.metrics.domain.model.EExpenseCategories;
import com.vp.analytics.ms.metrics.domain.model.ETransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, String> {

    @Query("SELECT SUM(t.value) FROM TransactionEntity t " +
            "WHERE t.clientId =:clientId AND t.transactionType = :transactionType " +
            "AND t.date BETWEEN : startDate and : endDate")
    BigDecimal sumValueByClientAndTransactionTypeAndPeriod(
            @Param("clientId") String clientId,
            @Param("transactionType") ETransactionType transactionType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(t.value) FROM TransactionEntity t " +
            "WHERE t.clientId =:clientId AND t.expenseCategory IN :expenseCategories " +
            "AND t.date BETWEEN : startDate and : endDate")
    BigDecimal sumValueByExpenseCategoriesAndPeriod(
            @Param("clientId") String clientId,
            @Param("expenseCategories") List<EExpenseCategories> expenseCategories,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(t) FROM TransactionEntity t " +
            "WHERE t.clientId = :clientId AND t.isNewCustomer = true " +
            "AND t.transactionType = com.vp.analytics.ms.metrics.domain.model.ETransactionType.REVENUE " +
            "AND t.date BETWEEN :startDate AND :endDate")
    long countNewCustomersByClientIdAndPeriod(
            @Param("clientId") String clientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(t) FROM TransactionEntity t " +
            "WHERE t.clientId = :clientId " +
            "AND t.transactionType = com.vp.analytics.ms.metrics.domain.model.ETransactionType.REVENUE " +
            "AND t.date BETWEEN :startDate AND :endDate")
    long countRevenueTransactionsByClientIdAndPeriod(
            @Param("clientId") String clientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT t.revenueCategory, SUM(t.value) FROM TransactionEntity t " +
            "WHERE t.clientId = :clientId AND t.revenueCategory IS NOT NULL " +
            "AND t.date BETWEEN :startDate AND :endDate GROUP BY t.revenueCategory")
    List<Object[]> sumRevenueGroupedByCategoryAndPeriod(
            @Param("clientId") String clientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT t.expenseCategory, SUM(t.value) FROM TransactionEntity t " +
            "WHERE t.clientId = :clientId AND t.expenseCategory IS NOT NULL " +
            "AND t.date BETWEEN :startDate AND :endDate GROUP BY t.expenseCategory")
    List<Object[]> sumExpenseGroupedByCategoryAndPeriod(
            @Param("clientId") String clientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
