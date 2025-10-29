package com.yotsume.orderapp.repository;

import com.yotsume.orderapp.entity.Order;
import com.yotsume.orderapp.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            select o from Order o
            where (:status is null or o.status = :status)
            and (:minAmount is null or o.totalAmount >= :minAmount)
            and (:maxAmount is null or o.totalAmount <= :maxAmount)
            and (:fromDate is null or o.orderDate >= :fromDate)
            and (:toDate is null or o.orderDate <= :toDate)
            """)
    List<Order> findFiltered(
            @Param("status") OrderStatus status,
            @Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
