package com.info.revolut.bank.transfer.services.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.info.revolut.bank.transfer.services.api.AccountInfo;

import io.dropwizard.hibernate.AbstractDAO;

public class AccountDAO extends AbstractDAO<AccountInfo> {

	public AccountDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Optional<AccountInfo> findById(String acctId) {
		return Optional.ofNullable(get(acctId));
	}

	public AccountInfo create(AccountInfo accountInfo) {
		return persist(accountInfo);
	}

	@SuppressWarnings("unchecked")
	public List<AccountInfo> findAll() {
		return list((Query<AccountInfo>) namedQuery("com.info.revolut.bank.transfer.services.api.AccountInfo.findAll"));
	}

}
