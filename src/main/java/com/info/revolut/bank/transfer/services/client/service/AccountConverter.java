package com.info.revolut.bank.transfer.services.client.service;

import com.info.revolut.bank.transfer.services.account.Account;
import com.info.revolut.bank.transfer.services.account.dto.NewAccountRequest;
import com.info.revolut.bank.transfer.services.account.dto.NewAccountResponse;

public class AccountConverter {

	public Account fromRequest(NewAccountRequest request) {
		return new Account(
				request.getName(),
				request.getCurrency()
		);
	}

	public NewAccountResponse toResponse(Account account) {
		NewAccountResponse response = new NewAccountResponse();

		response.setId(account.getId());
		response.setCurrency(account.getCurrency());
		response.setName(account.getName());

		return response;
	}
}
