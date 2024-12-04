package com.neoflex.calculator.services;

import com.neoflex.calculator.dtos.CreditDto;
import com.neoflex.calculator.dtos.LoanOfferDto;
import com.neoflex.calculator.dtos.LoanStatementRequestDto;
import com.neoflex.calculator.dtos.ScoringDataDto;
import com.neoflex.calculator.exceptions.CalculatorException;

import java.util.List;

public interface CalculatorService {
    CreditDto calculate(ScoringDataDto scoringDataDto) throws CalculatorException;
    List<LoanOfferDto> getOffers(LoanStatementRequestDto loanStatementRequestDto) throws CalculatorException;
}
