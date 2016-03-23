package com.kapital.transactions.stocks;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.Price;
import com.kapital.assets.Stock;
import com.kapital.transactions.Transaction;

public abstract class StocksTransaction extends Transaction{
	
	private final Stock stock;
	public static final BigDecimal COST = new BigDecimal("5");
	
	public StocksTransaction(DateTime transactionDate, Price transactionPrice, BigDecimal quantity, Stock stock) {
		super(transactionDate, transactionPrice, quantity);
		this.stock = stock;
	}

	@Override
	public BigDecimal getCost(){
		return COST;
	}
	
	public Stock getStock() {
		return stock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StocksTransaction other = (StocksTransaction) obj;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}
	
}
