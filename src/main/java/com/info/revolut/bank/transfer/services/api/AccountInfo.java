package com.info.revolut.bank.transfer.services.api;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "account")
@NamedQueries({ @NamedQuery(name = "com.info.revolut.bank.transfer.services.api.AccountInfo.findAll", query = "SELECT a FROM AccountInfo a") })
public class AccountInfo {

	@Column(name = "trans_amt", nullable = false)
	BigDecimal transAmt;
	
	@Id
	@Column(name = "acct_Id", nullable = false)
	String acctId;

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public AccountInfo(BigDecimal transAmt, String acctId) {
		super();
		this.transAmt = transAmt;
		this.acctId = acctId;
	}
	
	
	
	
}
