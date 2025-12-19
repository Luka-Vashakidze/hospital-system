package com.solvd.hospital.patterns.strategy;

import com.solvd.hospital.domain.Appointment;

import java.math.BigDecimal;

public class StandardBillingStrategy implements BillingStrategy {

    @Override
    public BigDecimal calculate(Appointment appointment) {
        if (appointment.getBillAmount() != null) {
            return appointment.getBillAmount();
        }
        return BigDecimal.ZERO;
    }
}
