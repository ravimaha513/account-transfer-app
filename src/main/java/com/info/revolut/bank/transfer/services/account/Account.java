package com.info.revolut.bank.transfer.services.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.info.revolut.bank.transfer.services.exceptions.NotEnoughMoneyException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "accounts")
@ApiModel(description = "All details about the Account. ")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(notes = "The database generated account ID")
	private long id;
	
	@ApiModelProperty(notes = "Currency: USD | EUR Only")
	private String currency;
	
	@ApiModelProperty(notes = "Can be checking/savings")
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private List<AccountOperation> accountOperations;

	public Account() {
	}

	public Account(String name, String currency) {
		this.name = name;
		this.currency = currency;
		this.accountOperations = new ArrayList<>();
	}

	@Transient
	public void makeDeposit(AccountOperation operation) {
		getAccountOperations().add(operation);
	}

	@Transient
	public void makeWithdraw(AccountOperation operation) {
		validateWithdraw(operation.getAmountInAccountCurrency());

		getAccountOperations().add(operation);
	}

	private void validateWithdraw(BigDecimal moneyToWithdraw) {
		if (getBalance().compareTo(moneyToWithdraw) < 0) {
			throw new NotEnoughMoneyException(getId());
		}
	}

	@Transient
	public BigDecimal getBalance() {
		BigDecimal balance = BigDecimal.ZERO;
		for (AccountOperation operation : getAccountOperations()) {
			balance = operation.apply(balance);
		}

		return balance.setScale(2, RoundingMode.DOWN);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AccountOperation> getAccountOperations() {
		return accountOperations;
	}

	public void setAccountOperations(List<AccountOperation> accountOperations) {
		this.accountOperations = accountOperations;
	}
}
