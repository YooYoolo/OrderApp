package com.yotsume.orderapp.dto.response;

import java.time.LocalDate;

public record ClientDto(
         Long id,
         String name,
         String email,
         LocalDate registrationDate
) {
}
