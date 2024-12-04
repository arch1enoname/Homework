package com.neoflex.calculator.utils;

import com.neoflex.calculator.dtos.CreditDto;
import com.neoflex.calculator.dtos.PaymentScheduleElementDto;
import com.neoflex.calculator.dtos.ScoringDataDto;
import com.neoflex.calculator.enums.Gender;
import com.neoflex.calculator.exceptions.CalculatorException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScoringDataValidator {

    public void validate(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException {
        validateAge(scoringDataDto);
        validateGender(scoringDataDto, creditDto);
        validatePosition(scoringDataDto, creditDto);
        validateSalary(scoringDataDto);
        validateEmploymentStatus(scoringDataDto, creditDto);
        validateWorkExperience(scoringDataDto);
        calculateMonthlyPayment(scoringDataDto, creditDto);
        generatePaymentSchedule(scoringDataDto, creditDto);
    }

    private void validateEmploymentStatus(ScoringDataDto scoringDataDto, CreditDto creditDto) throws CalculatorException {
        switch (scoringDataDto.getEmployment().getEmploymentStatus()) {
            case UNEMPLOYED:
                throw new CalculatorException("Безработный");
            case FREELANCE:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(2)));
                break;
            case BUSINESS_OWNER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(1)));
                break;
        }
    }

    private void validatePosition(ScoringDataDto scoringDataDto, CreditDto creditDto){
        switch (scoringDataDto.getEmployment().getPosition()) {
            case MIDDLE_MANAGER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-2)));
                break;
            case SENIOR_MANAGER:
                creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(-3)));
                break;
        }
    }

    private void validateSalary(ScoringDataDto scoringDataDto) throws CalculatorException {
        if (scoringDataDto.getAmount().doubleValue() > scoringDataDto.getTerm().doubleValue() * scoringDataDto.getEmployment().getSalary().doubleValue()) {
            throw new CalculatorException("Сумма займа больше, чем 24 зарплат");
        }
    }

    private void validateAge(ScoringDataDto scoringDataDto) throws CalculatorException {
        int age = Period.between(scoringDataDto.getBirthdate(), LocalDate.now()).getYears();
        if (age < 20 || age > 60) {
            throw new CalculatorException("Неподходящий возраст");
        }
    }

    private void validateGender(ScoringDataDto scoringDataDto, CreditDto creditDto) {
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
        if (scoringDataDto.getEmployment().getWorkExperienceTotal() < 18 || scoringDataDto.getEmployment().getWorkExperienceCurrent() < 3 ) {
            throw new CalculatorException("Неподходящий опыт работы");
        }
    }

    public void calculateMonthlyPayment(ScoringDataDto scoringDataDto, CreditDto creditDto) {
        BigDecimal annualRate = creditDto.getRate().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP); // Перевод процентов в доли
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP); // Годовая ставка -> месячная
        int termInMonths = scoringDataDto.getTerm();
        if (termInMonths <= 0 || monthlyRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Срок кредита и ставка должны быть положительными значениями.");
        }
        BigDecimal onePlusRatePowN = monthlyRate.add(BigDecimal.ONE).pow(termInMonths);
        BigDecimal numerator = monthlyRate.multiply(onePlusRatePowN);
        BigDecimal denominator = onePlusRatePowN.subtract(BigDecimal.ONE);
        BigDecimal monthlyPayment = scoringDataDto.getAmount()
                .multiply(numerator)
                .divide(denominator, 2, RoundingMode.HALF_UP);

        creditDto.setMonthlyPayment(monthlyPayment);
    }


    private void calculatePaymentSchedule(CreditDto creditDto) {
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        BigDecimal remainingDebt = creditDto.getAmount();
        BigDecimal totalPayment = creditDto.getMonthlyPayment();
        BigDecimal interestPayment;
        BigDecimal debtPayment;
        BigDecimal rate = creditDto.getRate();
        Integer term = creditDto.getTerm();

        for (int i = 1; i <= term; i++) {
            startDate = startDate.plusMonths(1);
            interestPayment = calculateInterestPayment(remainingDebt, rate);
            debtPayment = calculateDebtPayment(totalPayment, interestPayment);
            remainingDebt = calculateRemainingDebt(remainingDebt, debtPayment);

            if (i == term) {
                totalPayment = totalPayment.add(remainingDebt);
                remainingDebt = BigDecimal.valueOf(0);
            }

            paymentSchedule.add(
                    PaymentScheduleElementDto.builder()
                            .number(i)
                            .date(startDate)
                            .totalPayment(totalPayment.setScale(2, RoundingMode.HALF_UP))
                            .interestPayment(interestPayment.setScale(2, RoundingMode.HALF_UP))
                            .debtPayment(debtPayment.setScale(2, RoundingMode.HALF_UP))
                            .remainingDebt(remainingDebt.setScale(2, RoundingMode.HALF_UP))
                            .build()
            );
        }

        creditDto.setMonthlyPayment(paymentSchedule);
    }
}
