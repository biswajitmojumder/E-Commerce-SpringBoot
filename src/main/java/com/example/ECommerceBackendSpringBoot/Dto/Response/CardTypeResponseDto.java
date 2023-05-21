package com.example.ECommerceBackendSpringBoot.Dto.Response;

import com.example.ECommerceBackendSpringBoot.enums.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardTypeResponseDto {
    CardType cardType;
}
