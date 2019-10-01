package com.info.revolut.bank.transfer.services.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.info.revolut.bank.transfer.services.api.TransactionsInfo;

import io.dropwizard.hibernate.AbstractDAO;

public class TransactionDAO extends AbstractDAO<TransactionsInfo> {

	public TransactionDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	
	public Optional<TransactionsInfo> findById(String transactionId) {
		return Optional.ofNullable(get(transactionId));
	}

	public TransactionsInfo create(TransactionsInfo transactionsInfo) {
		return persist(transactionsInfo);
	}

	@SuppressWarnings("unchecked")
	public List<TransactionsInfo> findAll() {
		return list((Query<TransactionsInfo>) namedQuery("com.info.revolut.bank.transfer.services.api.UserInfo.findAll"));
	}


}
