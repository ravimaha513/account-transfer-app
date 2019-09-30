package com.info.revolut.bank.transfer.services.resources;

import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.info.revolut.bank.transfer.services.api.AccountInfo;
import com.info.revolut.bank.transfer.services.db.AccountDAO;

@Path("/v1/api/trans/")
@Produces(MediaType.APPLICATION_JSON)
public class AccountTransferResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountTransferResource.class);
	
	AccountDAO accountDAO ;

	public AccountTransferResource() {
		super();
	}

	@GET
	@Timed
	 @Path("/view_account")
	public AccountInfo sayHello(@QueryParam("accountId") Optional<String> accountId) {

		return accountDAO.findById(accountId.get()).get();
	}

	@POST
	@Path("/create_account")
	public void receiveHello(@Valid AccountInfo accountInfo) {
		LOGGER.info("Received a saying: {}", accountInfo);
		accountDAO.create(accountInfo);
	}

}
