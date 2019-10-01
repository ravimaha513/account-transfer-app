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
import com.info.revolut.bank.transfer.services.api.UserInfo;
import com.info.revolut.bank.transfer.services.db.UserDAO;

@Path("/v1/api/user/")
@Produces(MediaType.APPLICATION_JSON)
public class UserInfoResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoResource.class);
	
	UserDAO userDAO;

	public UserInfoResource(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}
	
	@GET
	@Timed
	@Path("/view_user")
	public UserInfo viewUserAccount(@QueryParam("userId") Optional<String> userId) {

		return userDAO.findById(userId.get()).get();
	}

	@POST
	@Path("/create_user")
	public void createUser(@Valid UserInfo userInfo) {
		LOGGER.info("Received a saying: {}", userInfo);
		userDAO.create(userInfo);
	}
	
}
