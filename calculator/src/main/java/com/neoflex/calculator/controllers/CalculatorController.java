package com.neoflex.calculator.controllers;


import com.neoflex.calculator.dtos.CreditDto;
import com.neoflex.calculator.dtos.LoanOfferDto;
import com.neoflex.calculator.dtos.LoanStatementRequestDto;
import com.neoflex.calculator.dtos.ScoringDataDto;
import com.neoflex.calculator.exceptions.CalculatorException;
import com.neoflex.calculator.services.CalculatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorServiceImpl calculatorService;

    @Autowired
    public CalculatorController(CalculatorServiceImpl calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> getOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        return ResponseEntity.ok(calculatorService.getOffers(loanStatementRequestDto));
    }

    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculate(@RequestBody ScoringDataDto scoringDataDto) throws CalculatorException {
        return ResponseEntity.ok(calculatorService.calculate(scoringDataDto));
    }
}
