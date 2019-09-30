package com.info.revolut.bank.transfer.services;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AccountTransferApplication extends Application<AccountTransferConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AccountTransferApplication().run(args);
    }

    @Override
    public String getName() {
        return "AccountTransfer";
    }

    @Override
    public void initialize(final Bootstrap<AccountTransferConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final AccountTransferConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
