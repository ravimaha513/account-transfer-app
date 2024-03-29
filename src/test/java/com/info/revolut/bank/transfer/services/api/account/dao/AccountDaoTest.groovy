package com.info.revolut.bank.transfer.services.api.account.dao

import com.info.revolut.bank.transfer.services.account.dao.AccountDao;
import com.info.revolut.bank.transfer.services.account.Account;
import com.info.revolut.bank.transfer.services.exceptions.AccountNotExistsException;
import com.info.revolut.bank.transfer.services.account.AccountOperation;

import io.dropwizard.testing.junit.DAOTestRule
import org.junit.Rule
import spock.lang.Specification
import spock.lang.Subject

class AccountDaoTest extends Specification {

    @Rule
    public DAOTestRule database = DAOTestRule.newBuilder()
            .addEntityClass(Account.class)
            .addEntityClass(AccountOperation.class)
            .build()

    @Subject dao = new AccountDao(database.getSessionFactory())

    def 'Should create, find and verify if account exists'() {
        when:
        def account = dao.create(new Account('name', 'USD'))

        then:
        assertAccount(account, 1, 'name', 'USD')

        when:
        account = dao.findById(1)

        then:
        assertAccount(account, 1, 'name', 'USD')

        when:
        def exists = dao.exists(1)

        then:
        exists
    }

    def 'Should throw exception if account is not found'() {
        when:
        dao.getOrThrowException(1)

        then:
        thrown(AccountNotExistsException)
    }

    def 'Should return account if exists and no exception is thrown'() {
        given:
        dao.create(new Account('name', 'USD'))

        when:
        dao.getOrThrowException(1)

        then:
        noExceptionThrown()
    }

    def 'Should indicate if account exists in db'() {
        when:
        dao.create(new Account('name', 'USD'))
        dao.create(new Account('name', 'EUR'))
        dao.create(new Account('name', 'PLN'))

        then:
        dao.exists(1)
        dao.exists(2)
        dao.exists(3)

        and:
        !dao.exists(0)
        !dao.exists(4)
    }

    void assertAccount(Account account, id, name, currency) {
        assert account.id == id
        assert account.name == name
        assert account.currency == currency
    }
}
