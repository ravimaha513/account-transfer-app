package com.info.revolut.bank.transfer.services.api;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "account")
@NamedQueries({ @NamedQuery(name = "com.info.revolut.bank.transfer.services.api.AccountInfo.findAll", query = "SELECT a FROM AccountInfo a") })
public class AccountInfo {

	@Id
	@Column(name = "acctId", nullable = false)
	int acctId;

	@Column(name = "current_Balance", nullable = false)
	BigDecimal currentBalance;

	@Column(name = "trans_list", nullable = false)
	//@OneToMany(cascade=CascadeType.ALL, mappedBy="acctInfo")
	List<TransactionsInfo> acctTransactions;

	//@OneToOne(mappedBy="accountInfo", cascade=CascadeType.ALL)
	@OneToOne
	@MapsId
	UserInfo user;

	public BigDecimal getTransAmt() {
		return currentBalance;
	}

	public void setTransAmt(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public int getAcctId() {
		return acctId;
	}

	public void setAcctId(int acctId) {
		this.acctId = acctId;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public List<TransactionsInfo> getAcctTransactions() {
		return acctTransactions;
	}

	public void setAcctTransactions(List<TransactionsInfo> acctTransactions) {
		this.acctTransactions = acctTransactions;
	}

	public UserInfo getUserInfo() {
		return user;
	}

	public void setUserInfo(UserInfo user) {
		this.user = user;
	}

	public AccountInfo(int acctId, BigDecimal currentBalance, List<TransactionsInfo> acctTransactions,
			UserInfo user) {
		super();
		this.acctId = acctId;
		this.currentBalance = currentBalance;
		this.acctTransactions = acctTransactions;
		this.user = user;
	}





}
