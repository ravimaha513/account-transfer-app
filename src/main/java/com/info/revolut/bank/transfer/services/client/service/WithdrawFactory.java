package com.info.revolut.bank.transfer.services.client.service;

import java.math.BigDecimal;
import java.util.Date;

import com.info.revolut.bank.transfer.services.account.AccountOperation;
import com.info.revolut.bank.transfer.services.account.WithdrawOperation;
import com.info.revolut.bank.transfer.services.client.service.operations.AbstractAccountOperationFactory;
import com.info.revolut.bank.transfer.services.currency.ExchangeRateService;


public class WithdrawFactory extends AbstractAccountOperationFactory {

	public WithdrawFactory(ExchangeRateService exchangeRateService) {
		super(exchangeRateService);
	}

	@Override
	protected AccountOperation createOperation(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency) {
		return new WithdrawOperation(amount, amountInAccountCurrency, currency, new Date());
	}
}
