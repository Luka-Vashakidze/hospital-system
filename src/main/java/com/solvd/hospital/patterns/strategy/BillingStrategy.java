package com.solvd.hospital.patterns.strategy;

import com.solvd.hospital.domain.Appointment;

import java.math.BigDecimal;

public interface BillingStrategy {
    BigDecimal calculate(Appointment appointment);
}
