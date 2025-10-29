package com.yotsume.orderapp.dto.request;

import com.yotsume.orderapp.entity.enums.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderCreationDto(

        @NotNull
        Long clientId,

        @NotNull
        LocalDate orderDate,

        @NotNull
        @DecimalMin(value = "0.01", message = "Сумма должна быть положительной")
        BigDecimal totalAmount,

        @NotNull
        OrderStatus status
) {
}
