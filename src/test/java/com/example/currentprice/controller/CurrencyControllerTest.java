package com.example.currentprice.controller;

import com.example.currentprice.model.Currency;
import com.example.currentprice.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        currencyRepository.deleteAll();
    }

    @Test
    void testCreateCurrency() throws Exception {
        String currencyJson = """
            {
                "code": "USD",
                "name": "United States Dollar",
                "rate": 97652.9094,
                "updatedTime": "2024-11-21T21:33:17",
                "symbol": "$"
            }
        """;

        mockMvc.perform(post("/api/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(currencyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.name").value("United States Dollar"));
    }

    @Test
    void testGetAllCurrencies() throws Exception {

        currencyRepository.save(new Currency("USD", "United States Dollar", BigDecimal.valueOf(97652.9094),
                LocalDateTime.now().toString(), "$"));

        mockMvc.perform(get("/api/currency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].code").value("USD"));
    }

    @Test
    void testGetCurrencyByCode() throws Exception {
        currencyRepository.save(new Currency("EUR", "Euro", BigDecimal.valueOf(93186.7536), LocalDateTime.now().toString(), "€"));

        mockMvc.perform(get("/api/currency/code/EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("EUR"))
                .andExpect(jsonPath("$.name").value("Euro"));
    }

    @Test
    void testUpdateCurrency() throws Exception {
        Currency currency = currencyRepository.save(new Currency("GBP", "British Pound", BigDecimal.valueOf(77526.84),
                LocalDateTime.now().toString(), "£"));

        String updatedCurrencyJson = """
            {
                "id": %d,
                "code": "GBP",
                "name": "Updated British Pound",
                "rate": 80000.00,
                "updatedTime": "2024-11-21T21:33:17",
                "symbol": "£"
            }
        """.formatted(currency.getId());

        mockMvc.perform(put("/api/currency/update/"+currency.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCurrencyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated British Pound"))
                .andExpect(jsonPath("$.rate").value(80000.00));
    }

    @Test
    void testDeleteCurrency() throws Exception {
        Currency currency = currencyRepository.save(new Currency("JPY", "Japanese Yen", BigDecimal.valueOf(110.50),
                LocalDateTime.now().toString(), "¥"));

        mockMvc.perform(delete("/api/currency/delete/" + currency.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Currency deleted successfully."));
    }

    @Test
    void testCreateDuplicateCurrency() throws Exception {
        currencyRepository.save(new Currency("USD", "United States Dollar", BigDecimal.valueOf(97652.9094),
                LocalDateTime.now().toString(), "$"));

        String duplicateCurrencyJson = """
            {
                "code": "USD",
                "name": "Duplicate Dollar",
                "rate": 100000.00,
                "updatedTime": "2024-11-21T21:33:17",
                "symbol": "$"
            }
        """;

        mockMvc.perform(post("/api/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateCurrencyJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateDuplicateCurrency_Failure() throws Exception {
        // 第一次插入
        currencyRepository.save(new Currency("USD", "United States Dollar", BigDecimal.valueOf(97652.9094),
                LocalDateTime.now().toString(), "$"));

        // 嘗試插入相同 code 的數據
        String duplicateCurrencyJson = """
        {
            "code": "USD",
            "name": "Duplicate Dollar",
            "rate": 100000.00,
            "updatedTime": "2024-11-21T21:33:17",
            "symbol": "$"
        }
    """;

        mockMvc.perform(post("/api/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateCurrencyJson))
                .andExpect(status().isBadRequest()) // 唯一性違反
                .andExpect(content().string("Currency with code USD already exists."));
    }

    @Test
    void testUpdateNonExistingCurrency_Failure() throws Exception {
        String nonExistingCurrencyJson = """
        {
            "code": "GBP",
            "name": "British Pound",
            "rate": 80000.00,
            "updatedTime": "2024-11-21T21:33:17",
            "symbol": "£"
        }
    """;

        mockMvc.perform(put("/api/currency/update/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nonExistingCurrencyJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Currency not found with id: 999"));
    }
}