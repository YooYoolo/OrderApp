package com.yotsume.orderapp.dto.request;

import com.yotsume.orderapp.entity.enums.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record InitialOrderDto(
        @NotNull LocalDate orderDate,
        @DecimalMin("0.01") BigDecimal totalAmount,
        OrderStatus status
) {
}
