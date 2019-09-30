package com.info.revolut.bank.transfer.services.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@NamedQueries({ @NamedQuery(name = "com.info.revolut.bank.transfer.services.api.UserInfo.findAll", query = "SELECT u FROM UserInfo u") })
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_name", nullable = false)
	String name;
	@Column(name = "user_account", nullable = false)
	AccountInfo acctInfo;

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public AccountInfo getAcctInfo() {
		return acctInfo;
	}

	public void setAcctInfo(AccountInfo acctInfo) {
		this.acctInfo = acctInfo;
	}

	public UserInfo(String name, AccountInfo acctInfo) {
		super();
		this.name = name;
		this.acctInfo = acctInfo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserInfo)) {
			return false;
		}

		final UserInfo that = (UserInfo) o;

		return Objects.equals(this.name, that.name) && Objects.equals(this.acctInfo.acctId, that.acctInfo.acctId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, acctInfo.acctId);
	}

}
