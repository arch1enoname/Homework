package com.neoflex.calculator.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentScheduleElementDto {

    @NotNull
    private Integer number;
    @NotNull
    private LocalDate date;
    @NotNull
    private BigDecimal totalPayment;
    @NotNull
    private BigDecimal interestPayment;
    @NotNull
    private BigDecimal debtPayment;
    @NotNull
    private BigDecimal remainingDebt;
}

