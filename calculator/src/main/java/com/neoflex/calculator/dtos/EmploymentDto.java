package com.neoflex.calculator.dtos;

import com.neoflex.calculator.enums.EmploymentStatus;
import com.neoflex.calculator.enums.Position;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmploymentDto {

    @NotNull
    private EmploymentStatus employmentStatus;

    @NotBlank
    private String employerINN;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal salary;

    @NotNull
    private Position position;

    @Min(0)
    private Integer workExperienceTotal;

    @Min(0)
    private Integer workExperienceCurrent;
}

