package com.solvd.hospital.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Insurance {
    private Long id;
    private Long insuranceTypeId;
    private String policyNumber;
    private boolean insured;
    private LocalDate expiryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInsuranceTypeId() {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(Long insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Insurance insurance = (Insurance) o;
        return Objects.equals(id, insurance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
