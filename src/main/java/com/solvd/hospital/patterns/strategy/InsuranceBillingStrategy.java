package com.solvd.hospital.patterns.strategy;

import com.solvd.hospital.domain.Appointment;

import java.math.BigDecimal;

public class InsuranceBillingStrategy implements BillingStrategy {

    private final BigDecimal discountFactor;

    public InsuranceBillingStrategy(BigDecimal discountFactor) {
        this.discountFactor = discountFactor;
    }

    @Override
    public BigDecimal calculate(Appointment appointment) {
        if (appointment.getBillAmount() == null) {
            return BigDecimal.ZERO;
        }
        return appointment.getBillAmount().multiply(discountFactor);
    }
}
