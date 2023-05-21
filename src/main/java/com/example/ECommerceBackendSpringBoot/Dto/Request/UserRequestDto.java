package com.example.ECommerceBackendSpringBoot.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    String userName;
    String emailId;
    int age;
    String mobNo;
    String address;
}
