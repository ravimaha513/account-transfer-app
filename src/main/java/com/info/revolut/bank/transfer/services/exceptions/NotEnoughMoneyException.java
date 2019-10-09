package com.info.revolut.bank.transfer.services.exceptions;

public class NotEnoughMoneyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ERROR_TEMPLATE = "Not enough money on account '%s'";

	public NotEnoughMoneyException(long accountId) {
		super(String.format(ERROR_TEMPLATE, accountId));
	}
}
