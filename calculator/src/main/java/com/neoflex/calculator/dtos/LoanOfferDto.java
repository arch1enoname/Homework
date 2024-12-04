package com.neoflex.calculator.dtos;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanOfferDto {

    @NotNull
    private UUID statementId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal requestedAmount;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal totalAmount;

    @NotNull
    @Min(1)
    private Integer term;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal monthlyPayment;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal rate = BigDecimal.valueOf(5.0);

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;
}
