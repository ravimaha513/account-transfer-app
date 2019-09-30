package com.info.revolut.bank.transfer.services.db;

import org.hibernate.SessionFactory;

import com.info.revolut.bank.transfer.services.api.UserInfo;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<UserInfo> {

	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}
