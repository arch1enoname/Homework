package com.neoflex.calculator.dtos;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreditDto {

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    @Min(1)
    private Integer term;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal monthlyPayment;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal rate;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal psk;

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;

    @NotNull
    private List<PaymentScheduleElementDto> paymentSchedule;
}

