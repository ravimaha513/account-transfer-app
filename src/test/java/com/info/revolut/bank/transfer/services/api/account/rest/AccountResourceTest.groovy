package com.info.revolut.bank.transfer.services.api.account.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.revolut.bank.transfer.services.AccountTransferApplication
import com.info.revolut.bank.transfer.services.AccountTransferConfiguration
import com.info.revolut.bank.transfer.services.account.dto.ExchangeRateDto
import com.info.revolut.bank.transfer.services.currency.dao.ExchangeRateDao
import groovy.json.JsonSlurper
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.ClassRule
import spock.lang.Specification
import spock.lang.Stepwise

import javax.ws.rs.client.Entity
import javax.ws.rs.core.Response

@Stepwise
class AccountResourceTest extends Specification {

    def objectMapper = new ObjectMapper()
    def jsonSlurper = new JsonSlurper()

    @ClassRule
    public static final DropwizardAppRule<AccountTransferConfiguration> RULE =
            new DropwizardAppRule<AccountTransferConfiguration>(AccountTransferApplication.class, ResourceHelpers.resourceFilePath('config-test.yml'))

    def setupSpec() {
        RULE.testSupport.before()
        RULE.application.run()

        addExchangeRate('EUR', 'USD', '1.15874')
    }

    def 'Should create account 1 with success - USD'() {
        when:
        def response = createAccount('account/create/input/account_create_ok_1.json')

        then:
        response.status == 200
        assertResponse(response, 'account/create/output/account_create_ok_1.json')
    }

    def 'Should create account 2 with success - USD'() {
        when:
        def response = createAccount('account/create/input/account_create_ok_2.json')

        then:
        response.status == 200
        assertResponse(response, 'account/create/output/account_create_ok_2.json')
    }

    def 'Should make deposit on account 1 with 500.10 USD'() {
        when:
        def response = deposit(1, 'account/deposit/input/account_deposit_ok_1.json')

        then:
        response.status == 200
        assertResponse(response, 'account/deposit/output/account_deposit_ok_1.json')
    }

    def 'Should make another deposit on account 1 with 200 EUR'() {
        when: '200 EUR = 231,748 USD'
        def response = deposit(1, 'account/deposit/input/account_deposit_ok_1_eur.json')

        then:
        response.status == 200
        assertResponse(response, 'account/deposit/output/account_deposit_ok_1_eur.json')
    }

    def 'Should fail to deposit money on non-existing account'() {
        when:
        def response = deposit(12, 'account/deposit/input/account_deposit_error_12.json')

        then:
        response.status == 404
        assertResponse(response, 'account/deposit/output/account_deposit_error_12.json')
    }

    def 'Should withdraw 150 EUR from account 1'() {
        when: '150 EUR = 173,811 USD'
        def response = withdraw(1, 'account/withdraw/input/account_withdraw_ok_1.json')

        then:
        response.status == 200
        assertResponse(response, 'account/withdraw/output/account_withdraw_ok_1.json')
    }

    def 'Should fail with withdraw 150 USD from account 2 as it is empty'() {
        when:
        def response = withdraw(2, 'account/withdraw/input/account_withdraw_error_2.json')

        then:
        response.status == 406
        assertResponse(response, 'account/withdraw/output/account_withdraw_error_2.json')
    }

    def 'Should get balance for account 1'() {
        when:
        def response = balance(1)

        then:
        response.status == 200
        assertResponse(response, 'account/balance/output/account_balance_ok_1.json')
    }

    def 'Should get balance for account 2'() {
        when:
        def response = balance(2)

        then:
        response.status == 200
        assertResponse(response, 'account/balance/output/account_balance_ok_2.json')
    }

    def 'Should transfer 200 USD from account 1 to 2'() {
        when:
        def response = transfer(1, 2, 'account/transfer/input/account_transfer_ok_1_2.json')

        then:
        response.status == 200
        assertResponse(response, 'account/transfer/output/account_transfer_ok_1_2.json')
    }

    def 'Should fail to transfer 10000 EUR from account 2 to 1'() {
        when:
        def response = transfer(2, 1, 'account/transfer/input/account_transfer_error_2_1.json')

        then:
        response.status == 406
        assertResponse(response, 'account/transfer/output/account_transfer_error_2_1.json')

        when: 'check the balance on account 1'
        response = balance(1)

        then:
        response.status == 200
        assertResponse(response, 'account/transfer/output/account_balance_after_error_1.json')

        when: 'check the balance on account 2'
        response = balance(2)

        then:
        response.status == 200
        assertResponse(response, 'account/transfer/output/account_balance_after_error_2.json')
    }

    def 'Should fail to transfer money when account is not in the system'() {
        when:
        def response = transfer(1, 10, 'account/transfer/input/account_transfer_error_1_10.json')

        then:
        response.status == 404
        assertResponse(response, 'account/transfer/output/account_transfer_error_1_10.json')
    }

    def createAccount(String requestJsonPath) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/account")
                .request()
                .post(Entity.json(jsonFromFile(requestJsonPath)))
    }

    def deposit(accountId, String requestJsonPath) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/account/${accountId}/deposit")
                .request()
                .post(Entity.json(jsonFromFile(requestJsonPath)))
    }

    def withdraw(accountId, String requestJsonPath) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/account/${accountId}/withdraw")
                .request()
                .post(Entity.json(jsonFromFile(requestJsonPath)))
    }

    def transfer(accountId1, accountId2, String requestJsonPath) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/account/${accountId1}/transfer/${accountId2}")
                .request()
                .post(Entity.json(jsonFromFile(requestJsonPath)))
    }

    def balance(accountId) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/account/${accountId}/balance")
                .request()
                .get()
    }

    def addExchangeRate(String fromCurrency, String toCurrency, String rate) {
        RULE.client()
                .target("http://localhost:${RULE.getLocalPort()}/exchangerate")
                .request()
                .post(Entity.json(new ExchangeRateDto(fromCurrency, toCurrency, rate)))
    }

    def jsonFromFile(String path) throws IOException {
        return objectMapper
                .readTree(getClass().getClassLoader().getResource(path))
                .toString()
    }

    void assertResponse(Response response, String pathToExpectedJson) {
        def result = response.readEntity(String)
        def expected = jsonFromFile(pathToExpectedJson)

        assert jsonSlurper.parseText(result) == jsonSlurper.parseText(expected)
    }
}
