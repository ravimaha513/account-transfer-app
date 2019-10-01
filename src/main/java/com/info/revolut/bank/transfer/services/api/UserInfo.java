package com.info.revolut.bank.transfer.services.api;

import java.util.Objects;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@NamedQueries({ @NamedQuery(name = "com.info.revolut.bank.transfer.services.api.UserInfo.findAll", query = "SELECT u FROM UserInfo u") })
public class UserInfo {

	@Id
	@Column(name = "user_id")
	private int userId ;

	@Column(name = "user_name", nullable = false)
	private String name;
	
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "acctId", referencedColumnName = "acctId")
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JoinColumn(name = "acctId", referencedColumnName = "acctId")
	AccountInfo accountInfo;

	
	public UserInfo(int userId, String name, AccountInfo accountInfo) {
		super();
		this.userId = userId;
		this.name = name;
		this.accountInfo = accountInfo;
	}

	
	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		if(userId == 0)
			userId = generateRandomDigits(8);
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public AccountInfo getAccountInfo() {
		return accountInfo;
	}


	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
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

		return Objects.equals(this.name, that.name) && Objects.equals(this.accountInfo.acctId, that.accountInfo.acctId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, accountInfo.acctId);
	}
	
	
	public static int generateRandomDigits(int n) {
	    int m = (int) Math.pow(10, n - 1);
	    return m + new Random().nextInt(9 * m);
	}

}
