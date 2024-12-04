package com.neoflex.calculator.services;

import com.neoflex.calculator.dtos.CreditDto;
import com.neoflex.calculator.dtos.LoanOfferDto;
import com.neoflex.calculator.dtos.LoanStatementRequestDto;
import com.neoflex.calculator.dtos.ScoringDataDto;
import com.neoflex.calculator.exceptions.CalculatorException;
import com.neoflex.calculator.utils.ScoringDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private final ScoringDataValidator scoringDataValidator;

    @Autowired
    public CalculatorServiceImpl(ScoringDataValidator scoringDataValidator) {
        this.scoringDataValidator = scoringDataValidator;
    }


    public CreditDto calculate(ScoringDataDto scoringDataDto) throws CalculatorException {
        CreditDto creditDto = new CreditDto();
        creditDto.setRate(BigDecimal.valueOf(20.0));
        scoringDataValidator.validate(scoringDataDto, creditDto);
        return creditDto;
    }

    @Override
    public List<LoanOfferDto> getOffers(LoanStatementRequestDto loanStatementRequestDto) throws CalculatorException {
        List<LoanOfferDto> loanOfferDtos = new ArrayList<>();

        return loanOfferDtos;
    }

//    private LoanOfferDto getLoanOfferDto(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
//        LoanOfferDto loanOfferDto = new LoanOfferDto();
//        loanOfferDto.setIsInsuranceEnabled(isInsuranceEnabled);
//        loanOfferDto.setIsSalaryClient(isSalaryClient);
//        loanOfferDto.setTerm();
//
//
//
//
//
//        return loanOfferDto;
//    }
}
