package com.info.revolut.bank.transfer.services;

import com.info.revolut.bank.transfer.services.account.Account;
import com.info.revolut.bank.transfer.services.account.AccountOperation;
import com.info.revolut.bank.transfer.services.account.DepositOperation;
import com.info.revolut.bank.transfer.services.account.WithdrawOperation;
import com.info.revolut.bank.transfer.services.account.dao.AccountDao;
import com.info.revolut.bank.transfer.services.account.rates.ExchangeRate;
import com.info.revolut.bank.transfer.services.client.service.AccountConverter;
import com.info.revolut.bank.transfer.services.client.service.AccountService;
import com.info.revolut.bank.transfer.services.client.service.DepositFactory;
import com.info.revolut.bank.transfer.services.client.service.WithdrawFactory;
import com.info.revolut.bank.transfer.services.currency.ExchangeRateService;
import com.info.revolut.bank.transfer.services.currency.MoneyExchangeRateService;
import com.info.revolut.bank.transfer.services.currency.dao.ExchangeRateDao;
import com.info.revolut.bank.transfer.services.exceptions.mappers.AccountNotExistsExceptionMapper;
import com.info.revolut.bank.transfer.services.exceptions.mappers.NotEnoughMoneyExceptionMapper;
import com.info.revolut.bank.transfer.services.resources.AccountResource;
import com.info.revolut.bank.transfer.services.resources.ExchangeRateResource;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class AccountTransferApplication extends Application<AccountTransferConfiguration> {

	public static void main(final String[] args) throws Exception {
		new AccountTransferApplication().run(args);
	}

	private final HibernateBundle<AccountTransferConfiguration> hibernate =
			new HibernateBundle<AccountTransferConfiguration>(Account.class, AccountOperation.class, ExchangeRate.class,
					DepositOperation.class, WithdrawOperation.class) {
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
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new MigrationsBundle<AccountTransferConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(AccountTransferConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
		bootstrap.addBundle(new SwaggerBundle<AccountTransferConfiguration>() {
	        @Override
	        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AccountTransferConfiguration configuration) {
	            return configuration.getSwagger();
	        }
	    });
		
	}

	public void run(AccountTransferConfiguration configuration, Environment environment) {
		AccountDao accountDao = new AccountDao(hibernate.getSessionFactory());
		AccountConverter accountConverter = new AccountConverter();

		ExchangeRateDao exchangeRateDao = new ExchangeRateDao(hibernate.getSessionFactory());
		ExchangeRateService exchangeRateService = new MoneyExchangeRateService(exchangeRateDao);

		DepositFactory depositFactory = new DepositFactory(exchangeRateService);
		WithdrawFactory withdrawFactory = new WithdrawFactory(exchangeRateService);

		AccountService accountService =
				new AccountService(accountDao, accountConverter, depositFactory, withdrawFactory);

		environment.jersey().register(new AccountResource(accountService));
		environment.jersey().register(new AccountNotExistsExceptionMapper());
		environment.jersey().register(new NotEnoughMoneyExceptionMapper());

		environment.jersey().register(new ExchangeRateResource(exchangeRateDao));
	}

}
