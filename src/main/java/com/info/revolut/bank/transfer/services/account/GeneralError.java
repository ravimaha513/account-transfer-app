package com.info.revolut.bank.transfer.services.account;

import java.io.Serializable;

public class GeneralError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1206028773245051820L;
	private String error;

	public GeneralError(String error) {
		this.error = error;
	}

	public GeneralError() {
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
