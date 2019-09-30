package com.info.revolut.bank.transfer.services;

import com.info.revolut.bank.transfer.services.api.UserInfo;
import com.info.revolut.bank.transfer.services.db.UserDAO;
import com.info.revolut.bank.transfer.services.resources.AccountTransferResource;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AccountTransferApplication extends Application<AccountTransferConfiguration> {

	public static void main(final String[] args) throws Exception {
		new AccountTransferApplication().run(args);
	}

	private final HibernateBundle<AccountTransferConfiguration> hibernateBundle = new HibernateBundle<AccountTransferConfiguration>(
			UserInfo.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(AccountTransferConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public String getName() {
		return "AccountTransfer";
	}

	@Override
	public void initialize(final Bootstrap<AccountTransferConfiguration> bootstrap) {
		bootstrap.addBundle(hibernateBundle);
	}

	@Override
	public void run(final AccountTransferConfiguration configuration, final Environment environment) {

		final UserDAO dao = new UserDAO(hibernateBundle.getSessionFactory());
		final AccountTransferResource resource = new AccountTransferResource();
		environment.jersey().register(resource);

	}

}
