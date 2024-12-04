package com.neoflex.calculator.dtos;

import com.neoflex.calculator.enums.Gender;
import com.neoflex.calculator.enums.MaritalStatus;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ScoringDataDto {

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    @Min(1)
    private Integer term;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotNull
    private Gender gender;

    @NotNull
    @Past
    private LocalDate birthdate;

    @NotBlank
    private String passportSeries;

    @NotBlank
    private String passportNumber;

    @NotNull
    @Past
    private LocalDate passportIssueDate;

    @NotBlank
    private String passportIssueBranch;

    @NotNull
    private MaritalStatus maritalStatus;

    @Min(0)
    private Integer dependentAmount;

    @NotNull
    private EmploymentDto employment;

    @NotBlank
    private String accountNumber;

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;
}
