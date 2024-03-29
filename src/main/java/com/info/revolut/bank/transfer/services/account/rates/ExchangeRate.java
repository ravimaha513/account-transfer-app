package com.info.revolut.bank.transfer.services.account.rates;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ExchangeRate {

	@EmbeddedId
	private ExchangeRateKey id;

	@Column(name = "exchangeRate", precision = 10, scale = 5)
	private BigDecimal exchangeRate;

	public ExchangeRate(ExchangeRateKey id, BigDecimal exchangeRate) {
		this.id = id;
		this.exchangeRate = exchangeRate;
	}

	public ExchangeRate() {
	}

	public ExchangeRateKey getId() {
		return id;
	}

	public void setId(ExchangeRateKey id) {
		this.id = id;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
}
