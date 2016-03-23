package com.kapital.transactions.bonds;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.Price;
import com.kapital.assets.Bond;
import com.kapital.transactions.Transaction;

public abstract class BondsTransaction extends Transaction {

	private final Bond bond;

	public BondsTransaction(DateTime transactionDate, Price transactionPrice, BigDecimal quantity, Bond bond) {
		super(transactionDate, transactionPrice, quantity);
		this.bond = bond;
	}

	@Override
	public BigDecimal getCost() {
		return (getQuantity().multiply(getPrice().getValue())).multiply(new BigDecimal("0.0001"));
	}

	public Bond getBond() {
		return bond;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bond == null) ? 0 : bond.hashCode());
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
		BondsTransaction other = (BondsTransaction) obj;
		if (bond == null) {
			if (other.bond != null)
				return false;
		} else if (!bond.equals(other.bond))
			return false;
		return true;
	}

}
