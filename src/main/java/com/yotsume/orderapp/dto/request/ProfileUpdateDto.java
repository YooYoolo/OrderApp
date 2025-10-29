package com.yotsume.orderapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProfileUpdateDto(

        @NotBlank
        @Size(max = 50)
        String address,

        @NotBlank
        @Pattern(regexp = "^\\+?[0-9]{10,15}$")
        String phone
) {
}
