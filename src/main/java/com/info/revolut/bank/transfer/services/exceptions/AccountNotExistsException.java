package com.info.revolut.bank.transfer.services.exceptions;

public class AccountNotExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ERROR_TEMPLATE = "Account with id '%s' does not exist";

	public AccountNotExistsException(long id) {
		super(String.format(ERROR_TEMPLATE, id));
	}
}
