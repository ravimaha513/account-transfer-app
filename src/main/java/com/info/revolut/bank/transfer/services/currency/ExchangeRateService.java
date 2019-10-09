package com.info.revolut.bank.transfer.services.currency;

import java.math.BigDecimal;

public interface ExchangeRateService {

	BigDecimal getExchangeRate(String fromCurrency, String toCurrency);

	BigDecimal convertMoneyByExchangeRate(BigDecimal amount, String fromCurrency, String toCurrency);
}
