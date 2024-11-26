package com.example.currentprice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyResponseDTO {
    private Long id;
    private String code;
    private String name;
    private BigDecimal rate;
    private String updatedTime;

    public CurrencyResponseDTO(Long id, String code, String name, BigDecimal rate, String updatedTime) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.updatedTime = updatedTime;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
