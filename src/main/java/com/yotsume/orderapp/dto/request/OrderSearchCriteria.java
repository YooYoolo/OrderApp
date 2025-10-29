package com.yotsume.orderapp.dto.request;

import com.yotsume.orderapp.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderSearchCriteria(
        OrderStatus status,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        LocalDate fromDate,
        LocalDate toDate
) {
}
