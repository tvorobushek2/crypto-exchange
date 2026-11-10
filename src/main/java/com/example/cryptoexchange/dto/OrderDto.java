package com.example.cryptoexchange.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDto {

    @NotNull
    private Long userId;

    @NotBlank
    private String cryptoSymbol;

    @NotNull
    @Min(0)
    private Double amount;

    @NotNull
    @Min(0)
    private Double price;

    @NotBlank
    private String type;

    private String status;
}