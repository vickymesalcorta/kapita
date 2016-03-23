package com.kapital.transactions;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.InsufficientAssetsException;
import com.kapital.Price;

public abstract class Transaction {
	
	private final DateTime date;
	private final Price price;
	private final BigDecimal quantity;
	
	public Transaction(DateTime date, Price price, BigDecimal quantity){
		this.date = date;
		this.price = price;
		this.quantity = quantity;
	}
	
	public abstract void accept(TransactionVisitor visitor) throws InsufficientAssetsException;
	public abstract BigDecimal getCost();

	public Price getPrice() {
		return price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
	
}
