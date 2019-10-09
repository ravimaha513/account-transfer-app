package com.info.revolut.bank.transfer.services.client.service;

import java.math.BigDecimal;

import com.info.revolut.bank.transfer.services.account.Account;
import com.info.revolut.bank.transfer.services.account.dao.AccountDao;
import com.info.revolut.bank.transfer.services.account.dto.BalanceResponse;
import com.info.revolut.bank.transfer.services.account.dto.MoneyOperationRequest;
import com.info.revolut.bank.transfer.services.account.dto.MoneyOperationResponse;
import com.info.revolut.bank.transfer.services.account.dto.MoneyOperationResponse.MoneyDto;
import com.info.revolut.bank.transfer.services.account.dto.MoneyOperationResponse.Status;
import com.info.revolut.bank.transfer.services.account.dto.NewAccountRequest;
import com.info.revolut.bank.transfer.services.account.dto.NewAccountResponse;
import com.info.revolut.bank.transfer.services.account.dto.TransferResponse;
import com.info.revolut.bank.transfer.services.currency.util.MoneyFormatter;


public class AccountService {

	private final AccountDao accountDao;
	private final AccountConverter converter;
	private final DepositFactory depositFactory;
	private final WithdrawFactory withdrawFactory;

	public AccountService(AccountDao accountDao, AccountConverter converter, DepositFactory depositFactory,
			WithdrawFactory withdrawFactory) {
		this.accountDao = accountDao;
		this.converter = converter;
		this.depositFactory = depositFactory;
		this.withdrawFactory = withdrawFactory;
	}

	public NewAccountResponse createAccount(NewAccountRequest request) {
		Account account = accountDao.create(createAccountObject(request));
		return converter.toResponse(account);
	}

	public MoneyOperationResponse makeDeposit(long accountId, MoneyOperationRequest request) {

		Account account = accountDao.getOrThrowException(accountId);
		BigDecimal amount = MoneyFormatter.parse(request.getAmount(), 2);

		account.makeDeposit(
				depositFactory.create(amount, request.getCurrency(), account.getCurrency())
		);

		return okResponse(account, "deposit", request);
	}

	private MoneyOperationResponse okResponse(Account account, String operation, MoneyOperationRequest request) {
		return new MoneyOperationResponse(
				account.getName(),
				MoneyFormatter.format(account.getBalance()),
				Status.ok(operation, new MoneyDto(request.getAmount(), request.getCurrency()))
		);
	}

	public MoneyOperationResponse makeWithdraw(long accountId, MoneyOperationRequest request) {
		Account account = accountDao.getOrThrowException(accountId);
		BigDecimal amount = MoneyFormatter.parse(request.getAmount(), 2);

		account.makeWithdraw(
				withdrawFactory.create(amount, request.getCurrency(), account.getCurrency())
		);

		return okResponse(account, "withdraw", request);
	}

	private Account createAccountObject(NewAccountRequest request) {
		return converter.fromRequest(request);
	}

	public BalanceResponse getBalance(long accountId) {
		Account account = accountDao.getOrThrowException(accountId);

		return new BalanceResponse(account.getName(), MoneyFormatter.format(account.getBalance()),
				account.getCurrency());
	}

	public TransferResponse transferMoney(long fromAccountId, long toAccountId, MoneyOperationRequest request) {
		Account fromAccount = accountDao.getOrThrowException(fromAccountId);
		Account toAccount = accountDao.getOrThrowException(toAccountId);

		BigDecimal transferAmount = MoneyFormatter.parse(request.getAmount(), 2);
		String transferCurrency = request.getCurrency();

		fromAccount.makeWithdraw(
				withdrawFactory.create(transferAmount, transferCurrency, fromAccount.getCurrency())
		);

		toAccount.makeDeposit(
				depositFactory.create(transferAmount, transferCurrency, toAccount.getCurrency())
		);

		return new TransferResponse(
				new BalanceResponse(fromAccount.getName(), MoneyFormatter.format(fromAccount.getBalance()),
						fromAccount.getCurrency()),
				new BalanceResponse(toAccount.getName(), MoneyFormatter.format(toAccount.getBalance()),
						toAccount.getCurrency()),
				Status.ok("transfer", new MoneyDto(request.getAmount(), request.getCurrency()))
		);
	}
}
