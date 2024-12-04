package com.neoflex.calculator.dtos;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanStatementRequestDto {

    @NotNull
    @DecimalMin(value = "20000.0")
    private BigDecimal amount;

    @NotNull
    @Min(6)
    private Integer term;

    @NotBlank
    @Size(min = 2, max = 60)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 60)
    private String lastName;

    private String middleName;

    @Email
    private String email;

    @Past
    @Pattern(regexp = "yyyy-MM-dd", message = "Invalid birthdate")
    private LocalDate birthdate;

    @Pattern(regexp = "\\d{4}", message = "Invalid passport series")
    private String passportSeries;

    @Pattern(regexp = "\\d{6}", message = "Invalid passport number")
    private String passportNumber;
}

