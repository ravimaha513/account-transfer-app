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
import com.info.revolut.bank.transfer.services.api.TransactionsInfo;
import com.info.revolut.bank.transfer.services.db.TransactionDAO;

@Path("/v1/api/trans/")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionsInfoResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsInfoResource.class);
	
	TransactionDAO transDAO;

	public TransactionsInfoResource(TransactionDAO transDAO) {
		super();
		this.transDAO = transDAO;
	}
	
	@GET
	@Timed
	@Path("/view_transaction")
	public TransactionsInfo viewUserAccount(@QueryParam("userId") Optional<String> userId) {

		return transDAO.findById(userId.get()).get();
	}

	@POST
	@Path("/create_transaction")
	public void createUser(@Valid TransactionsInfo transactionInfo) {
		LOGGER.info("Received a saying: {}", transactionInfo);
		transDAO.create(transactionInfo);
	}
}
