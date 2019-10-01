package com.info.revolut.bank.transfer.services.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transactions_info")
public class TransactionsInfo {
	
	@Id
	@Column(name = "transaction_Id", nullable = false)
	private UUID transactionId = UUID.randomUUID();
	
	@Column(name = "transaction_time", nullable = false)
	private long timestamp = Instant.now().toEpochMilli();
	
	@Column(name = "transaction_amt", nullable = false)
	private BigDecimal transcAmt;
	
	@Column(name = "transaction_account_Id", nullable = false)
	@ManyToOne
	@JoinColumn(name="FK_acctInfo", nullable=false)
	private String accountId;

	public UUID getTransactionId() {
		return transactionId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public BigDecimal getTranscAmt() {
		return transcAmt;
	}

	public void setTranscAmt(BigDecimal transcAmt) {
		this.transcAmt = transcAmt;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	

}
