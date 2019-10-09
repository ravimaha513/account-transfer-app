package com.info.revolut.bank.transfer.services.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

@Entity
@DiscriminatorValue("withdraw")
@ApiModel(description = "All details about the WithdrawOperation. ")
public class WithdrawOperation extends AccountOperation {

	public WithdrawOperation(BigDecimal amount, BigDecimal amountInAccountCurrency, String currency, Date createdOn) {
		super(amount, amountInAccountCurrency, currency, createdOn);
	}

	public WithdrawOperation() {
	}

	@Override
	BigDecimal apply(BigDecimal actualBalance) {
		return actualBalance.subtract(getAmountInAccountCurrency());
	}
}
