package com.info.revolut.bank.transfer.services.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

@Entity
@DiscriminatorValue("deposit")
@ApiModel(description = "All details about the DepositOperation. ")
public class DepositOperation extends AccountOperation {

	public DepositOperation(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency, Date createdOn) {
		super(amount, amountInAccountCurrency, currency, createdOn);
	}

	public DepositOperation() {
	}

	@Override
	BigDecimal apply(BigDecimal actualBalance) {
		return actualBalance.add(getAmountInAccountCurrency());
	}
}
