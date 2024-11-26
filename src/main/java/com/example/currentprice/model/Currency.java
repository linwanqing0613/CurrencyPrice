package com.example.currentprice.model;

import jakarta.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    @Column(precision = 20, scale = 8)
    private BigDecimal rate;

    @Column(name = "updated_time")
    private String updatedTime;

    private String symbol;

    public Currency() {
    }

    public Currency(String code, String name, BigDecimal rate, String updatedTime, String symbol) {
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.updatedTime = updatedTime;
        this.symbol = symbol;
    }

    // Getters and Setters

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}