package com.neoflex.calculator.utils;

import com.neoflex.calculator.dtos.LoanStatementRequestDto;
import com.neoflex.calculator.exceptions.CalculatorException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PreScoringDataValidator {
    private void validateBirthdate(LoanStatementRequestDto loanStatementRequestDto) {
        int age = Period.between(loanStatementRequestDto.getBirthdate(), LocalDate.now()).getYears();
        if (18 > age) {
            throw new CalculatorException("Вам нет 18");
        }
    }
}
