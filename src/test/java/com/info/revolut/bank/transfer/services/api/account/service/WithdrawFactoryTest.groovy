package com.info.revolut.bank.transfer.services.api.account.service
import com.info.revolut.bank.transfer.services.client.service.WithdrawFactory
import com.info.revolut.bank.transfer.services.currency.ExchangeRateService
import spock.lang.Specification
import spock.lang.Subject

class WithdrawFactoryTest extends Specification {

    def exchangeRateService = Mock(ExchangeRateService)

    @Subject withdrawExecutor = new WithdrawFactory(exchangeRateService)

    def 'Should create withdraw operation'() {
        when:
        def operation = withdrawExecutor.create(120.45, 'USD', 'EUR')

        then:
        1 * exchangeRateService.convertMoneyByExchangeRate(120.45, 'USD', 'EUR') >> 155.678

        and:
        operation.currency == 'USD'
        operation.amount == 120.45
        operation.amountInAccountCurrency == 155.678
    }
}
