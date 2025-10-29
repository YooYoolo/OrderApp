package com.yotsume.orderapp.dto.response;

public record ProfileDto(
        Long id,
        String address,
        String phone,
        Long clientId
) {
}
