package com.info.revolut.bank.transfer.services;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class AccountTransferConfiguration extends Configuration {

	@Valid
	@NotNull
	private DataSourceFactory database = new DataSourceFactory();

	@Valid @NotNull
	@JsonProperty("swagger")
	private final SwaggerBundleConfiguration swaggerBundleConfiguration = new SwaggerBundleConfiguration();

	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

	@JsonProperty("database")
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.database = dataSourceFactory;
	}

	@JsonProperty("swagger")
	public SwaggerBundleConfiguration getSwagger() {
		return swaggerBundleConfiguration;
	}


}
