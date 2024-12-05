package com.neoflex.calculator.utils;

import com.neoflex.calculator.dtos.CreditDto;
import com.neoflex.calculator.dtos.PaymentScheduleElementDto;
import com.neoflex.calculator.dtos.ScoringDataDto;
import com.neoflex.calculator.enums.Gender;
import com.neoflex.calculator.exceptions.CalculatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ScoringDataValidator {

    public void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException {
        log.debug("Validating scoring data");

        validateAge(scoringDataDto);
        validateGender(scoringDataDto, creditDto);
        validatePosition(scoringDataDto, creditDto);
        validateSalary(scoringDataDto);
        validateEmploymentStatus(scoringDataDto, creditDto);
        validateWorkExperience(scoringDataDto);

    }

    private void validateEmploymentStatus(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException {
        log.debug("Validating employment status");
        switch (scoringDataDto.getEmployment().getEmploymentStatus()) {
            case UNEMPLOYED:
                throw new CalculatorException("Безработный");
            case FREELANCE:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(2)));
                break;
            case BUSINESS_OWNER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(1)));
                break;
            case FULL_TIME:
                break;
            default:
                throw new CalculatorException("Несуществующий статус");
        }
    }

    private void validatePosition(ScoringDataDto scoringDataDto, CreditDto creditDto){
        log.debug("Validating position");
        switch (scoringDataDto.getEmployment().getPosition()) {
            case MIDDLE_MANAGER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-2)));
                break;
            case SENIOR_MANAGER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-3)));
                break;
            default:
                throw new CalculatorException("Несуществующая должность");
        }
    }

    private void validateSalary(ScoringDataDto scoringDataDto) throws CalculatorException {
        log.debug("Validating salary");
        if (scoringDataDto.getAmount().doubleValue() > scoringDataDto.getTerm().doubleValue() * scoringDataDto.getEmployment().getSalary().doubleValue()) {
            throw new CalculatorException("Сумма займа больше, чем 24 зарплат");
        }
    }

    private void validateAge(ScoringDataDto scoringDataDto) throws CalculatorException {
        log.debug("Validating age");
        int age = Period.between(scoringDataDto.getBirthdate(), LocalDate.now()).getYears();
        if (age < 20 || age > 60) {
            throw new CalculatorException("Неподходящий возраст");
        }
    }

    private void validateGender(ScoringDataDto scoringDataDto, CreditDto creditDto) {
        log.debug("Validating gender");
        int age = Period.between(scoringDataDto.getBirthdate(), LocalDate.now()).getYears();
        if (age > 32 && age < 60 && scoringDataDto.getGender().equals(Gender.FEMALE)) {
            creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-3)));
        } else if (age < 55 && age > 30 && scoringDataDto.getGender().equals(Gender.MALE)) {
            creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-3)));
        } else if (scoringDataDto.getGender().equals(Gender.NON_BINARY)) {
            creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(7)));
        }
    }

    private void validateWorkExperience(ScoringDataDto scoringDataDto) throws CalculatorException {
        log.debug("Validating work experience");
        if (scoringDataDto.getEmployment().getWorkExperienceTotal() < 18 || scoringDataDto.getEmployment().getWorkExperienceCurrent() < 3 ) {
            throw new CalculatorException("Неподходящий опыт работы");
        }
    }
}
