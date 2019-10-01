package com.info.revolut.bank.transfer.services.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.info.revolut.bank.transfer.services.api.UserInfo;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<UserInfo> {

	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public Optional<UserInfo> findById(String userId) {
		return Optional.ofNullable(get(userId));
	}

	public UserInfo create(UserInfo userInfo) {
		return persist(userInfo);
	}

	@SuppressWarnings("unchecked")
	public List<UserInfo> findAll() {
		return list((Query<UserInfo>) namedQuery("com.info.revolut.bank.transfer.services.api.UserInfo.findAll"));
	}


}
