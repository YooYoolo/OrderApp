package com.yotsume.orderapp.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

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

        List<Long> couponIds
) {
}
