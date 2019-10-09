package com.info.revolut.bank.transfer.services.account.dto;

import com.info.revolut.bank.transfer.services.account.dto.MoneyOperationResponse.Status;

public class TransferResponse {

	private BalanceResponse from;
	private BalanceResponse to;
	private Status status;

	public TransferResponse(BalanceResponse from, BalanceResponse to, Status status) {
		this.from = from;
		this.to = to;
		this.status = status;
	}

	public TransferResponse() {
	}

	public BalanceResponse getFrom() {
		return from;
	}

	public void setFrom(BalanceResponse from) {
		this.from = from;
	}

	public BalanceResponse getTo() {
		return to;
	}

	public void setTo(BalanceResponse to) {
		this.to = to;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
