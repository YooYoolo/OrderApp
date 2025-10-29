package com.yotsume.orderapp.dto.response;

import java.math.BigDecimal;

public record CouponDto(
        Long id,
        String code,
        BigDecimal discount
) {
}
