package com.info.revolut.bank.transfer.services.resources;

import java.math.BigDecimal;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.info.revolut.bank.transfer.services.account.dto.ExchangeRateDto;
import com.info.revolut.bank.transfer.services.currency.dao.ExchangeRateDao;
import com.info.revolut.bank.transfer.services.currency.util.MoneyFormatter;

import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;

@Api("/exchangerate")
@Path("/exchangerate")
@Produces(MediaType.APPLICATION_JSON)
public class ExchangeRateResource {

	private final ExchangeRateDao dao;

	public ExchangeRateResource(ExchangeRateDao dao) {
		this.dao = dao;
	}

	@POST
	@UnitOfWork
	public Response addExchangeRate(ExchangeRateDto request) {

		BigDecimal rate = MoneyFormatter.parse(request.getRate(), 5);

		return Response.ok(
				dao.save(request.getFromCurrency(), request.getToCurrency(), rate)
		).build();
	}

}
