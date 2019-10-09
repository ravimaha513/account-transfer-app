package com.info.revolut.bank.transfer.services.exceptions.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.info.revolut.bank.transfer.services.account.GeneralError;
import com.info.revolut.bank.transfer.services.exceptions.AccountNotExistsException;


public class AccountNotExistsExceptionMapper implements ExceptionMapper<AccountNotExistsException> {

	@Override
	public Response toResponse(AccountNotExistsException e) {
		return Response.status(Status.NOT_FOUND)
				.entity(new GeneralError(e.getMessage()))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
}
