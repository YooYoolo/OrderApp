package com.yotsume.orderapp.dto.response;

import com.yotsume.orderapp.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderDto(
         Long id,
         LocalDate orderDate,
         BigDecimal totalAmount,
         OrderStatus status,
         Long clientId
) {
}
