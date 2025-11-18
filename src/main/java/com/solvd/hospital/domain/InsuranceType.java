package com.solvd.hospital.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class InsuranceType {
    private Long id;
    private String name;
    private BigDecimal coveragePercentage;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCoveragePercentage() {
        return coveragePercentage;
    }

    public void setCoveragePercentage(BigDecimal coveragePercentage) {
        this.coveragePercentage = coveragePercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsuranceType that = (InsuranceType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
