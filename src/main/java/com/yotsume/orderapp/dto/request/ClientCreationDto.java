package com.yotsume.orderapp.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

public record ClientCreationDto(
        @NotNull
        @Size(max = 30)
        String name,

        @NotBlank
        @Email
        @Size(max = 50)
        String email,

        @NotBlank
        @Size(max = 50)
        String address,

        @NotBlank
        @Pattern(regexp = "^\\+?[0-9]{10,15}$")
        String phone,

        List<InitialOrderDto> initialOrders,

        Set<Long> couponIds
) {
}
