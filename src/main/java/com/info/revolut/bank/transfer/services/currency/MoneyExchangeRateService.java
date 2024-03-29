package com.info.revolut.bank.transfer.services.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.info.revolut.bank.transfer.services.account.rates.ExchangeRate;
import com.info.revolut.bank.transfer.services.currency.dao.ExchangeRateDao;


public class MoneyExchangeRateService implements ExchangeRateService {

	private final ExchangeRateDao dao;

	public MoneyExchangeRateService(ExchangeRateDao dao) {
		this.dao = dao;
	}

	@Override
	public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
		if (differentCurrencies(fromCurrency, toCurrency)) {
			ExchangeRate exchangeRate = dao.get(fromCurrency, toCurrency);
			return exchangeRate.getExchangeRate();
		}

		return BigDecimal.ONE;
	}

	@Override
	public BigDecimal convertMoneyByExchangeRate(BigDecimal amount, String fromCurrency, String toCurrency) {
		BigDecimal exchangeRate = getExchangeRate(fromCurrency, toCurrency);

		return amount.multiply(exchangeRate).setScale(2, RoundingMode.DOWN);
	}

	private boolean differentCurrencies(String fromCurrency, String toCurrency) {
		return !fromCurrency.equalsIgnoreCase(toCurrency);
	}
}
