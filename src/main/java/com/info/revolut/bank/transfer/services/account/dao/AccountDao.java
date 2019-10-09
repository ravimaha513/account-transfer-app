package com.info.revolut.bank.transfer.services.account.dao;

import org.hibernate.SessionFactory;

import com.info.revolut.bank.transfer.services.account.Account;
import com.info.revolut.bank.transfer.services.exceptions.AccountNotExistsException;

import io.dropwizard.hibernate.AbstractDAO;

public class AccountDao extends AbstractDAO<Account> {

	public AccountDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public boolean exists(long id) {
		return findById(id) != null;
	}

	public Account findById(long id) {
		return get(id);
	}

	public Account getOrThrowException(long id) {
		Account account = get(id);
		if (account != null) {
			return account;
		}

		throw new AccountNotExistsException(id);
	}

	public Account create(Account account) {
		return persist(account);
	}
}
