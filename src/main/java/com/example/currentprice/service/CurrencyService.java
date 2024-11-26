package com.example.currentprice.service;

import com.example.currentprice.repository.CurrencyRepository;
import com.example.currentprice.model.Currency;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;


    public Currency createCurrency(Currency currency) {
        Optional<Currency> existingCurrency = currencyRepository.findByCode(currency.getCode());
        if(existingCurrency.isPresent()){
            throw new RuntimeException("Currency with code " + currency.getCode() + " already exists.");
        }
        return currencyRepository.save(currency);
    }
    public Currency UpdateCurrency(Long id, Currency currencyDetails) {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        if(currencyOptional.isPresent()){
            Currency currency = currencyOptional.get();
            currency.setCode(currencyDetails.getCode());
            currency.setName(currencyDetails.getName());
            currency.setRate(currencyDetails.getRate());
            currency.setUpdatedTime(currencyDetails.getUpdatedTime());
            currency.setSymbol(currencyDetails.getSymbol());

            return currencyRepository.save(currency);
        }else{
            throw new RuntimeException("Currency not found with id: " + id);
        }
    }
    public Currency getCurrencyById(Long id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
    }

    public Currency getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Currency not found with code: " + code));
    }

    public void deleteCurrency(Long id) {
        if (!currencyRepository.existsById(id)) {
            throw new RuntimeException("Currency not found with id: " + id);
        }
        currencyRepository.deleteById(id);
    }

    public void updateCurrencyDataFromCoinDesk() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";

        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.getBody());

            String updatedTime = jsonObject.getJSONObject("time").getString("updatedISO");

            JSONObject bpi = jsonObject.getJSONObject("bpi");

            for (String key : bpi.keySet()) {
                JSONObject currencyData = bpi.getJSONObject(key);

                String code = currencyData.getString("code");
                String name = currencyData.getString("description");
                String symbol = currencyData.getString("symbol");
                BigDecimal rate = currencyData.getBigDecimal("rate_float");

                Currency currency = currencyRepository.findByCode(code)
                        .orElse(new Currency());
                currency.setCode(code);
                currency.setName(name);
                currency.setRate(rate);
                currency.setSymbol(symbol);
                currency.setUpdatedTime(updatedTime);

                currencyRepository.save(currency);
            }
        }
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
