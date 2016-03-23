package com.kapital.transactions.cash;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.Price;
import com.kapital.assets.Cash;
import com.kapital.transactions.Transaction;

public abstract class CashTransaction extends Transaction{
	
	private final Cash cash;
	private static final BigDecimal COST = new BigDecimal("0");
	
	public CashTransaction(DateTime date, Price price, BigDecimal quantity, Cash cash) {
		super(date, price, quantity);
		this.cash = cash;
	}
	
	@Override
	public BigDecimal getCost(){
		return COST;
	}
	
	public Cash getCash(){
		return cash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cash == null) ? 0 : cash.hashCode());
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
		CashTransaction other = (CashTransaction) obj;
		if (cash == null) {
			if (other.cash != null)
				return false;
		} else if (!cash.equals(other.cash))
			return false;
		return true;
	}

}
