package com.example.currentprice.controller;

import com.example.currentprice.dto.CurrencyResponseDTO;
import com.example.currentprice.model.Currency;
import com.example.currentprice.service.CurrencyService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;


    @GetMapping("/id/{id}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable Long id) {
        return ResponseEntity.ok(currencyService.getCurrencyById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Currency> getCurrencyByCode(@PathVariable String code) {
        return ResponseEntity.ok(currencyService.getCurrencyByCode(code));
    }

    @PostMapping
    public ResponseEntity<?> createCurrency(@RequestBody Currency currency) {
        try {
            Currency createdCurrency = currencyService.createCurrency(currency);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCurrency);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
        try {
            Currency updateCurrency = currencyService.UpdateCurrency(id, currency);
            return ResponseEntity.status(HttpStatus.OK).body(updateCurrency);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable Long id) {
        try {
            currencyService.deleteCurrency(id);
            return ResponseEntity.status(HttpStatus.OK).body("Currency deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/update")
    public ResponseEntity<String> updateCurrencies() {
        try {
            currencyService.updateCurrencyDataFromCoinDesk();
            return ResponseEntity.status(HttpStatus.OK).body("Currency data updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponseDTO>> getAllCurrencies() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        List<CurrencyResponseDTO> response = currencies.stream()
                .map(currency -> new CurrencyResponseDTO(
                        currency.getId(),
                        currency.getCode(),
                        currency.getName(),
                        currency.getRate(),
                        currency.getUpdatedTime()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
