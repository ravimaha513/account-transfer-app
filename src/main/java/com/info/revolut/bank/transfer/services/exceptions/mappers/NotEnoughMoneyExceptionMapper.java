package com.info.revolut.bank.transfer.services.exceptions.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.info.revolut.bank.transfer.services.account.GeneralError;
import com.info.revolut.bank.transfer.services.exceptions.NotEnoughMoneyException;


public class NotEnoughMoneyExceptionMapper implements ExceptionMapper<NotEnoughMoneyException> {

	@Override
	public Response toResponse(NotEnoughMoneyException e) {
		return Response.status(Status.NOT_ACCEPTABLE)
				.entity(new GeneralError(e.getMessage()))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
}
