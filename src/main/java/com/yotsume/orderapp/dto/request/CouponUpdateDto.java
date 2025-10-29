package com.yotsume.orderapp.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CouponUpdateDto(
        @NotBlank
        @Size(max = 20)
        String code,

        @NotNull
        @DecimalMin(value = "0.01")
        @DecimalMax(value = "1.00")
        BigDecimal discount
) {
}
