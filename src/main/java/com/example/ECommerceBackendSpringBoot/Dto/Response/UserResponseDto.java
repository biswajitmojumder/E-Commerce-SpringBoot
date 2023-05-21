package com.example.ECommerceBackendSpringBoot.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    String userName;
    String emailId;
    int age;
    String mobNo;
}
