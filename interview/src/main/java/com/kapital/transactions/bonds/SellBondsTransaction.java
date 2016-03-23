package com.kapital.transactions.bonds;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.InsufficientAssetsException;
import com.kapital.Price;
import com.kapital.assets.Bond;
import com.kapital.transactions.TransactionVisitor;

public class SellBondsTransaction extends BondsTransaction {

	public SellBondsTransaction(DateTime purchaseDate, Price purchasePrice, BigDecimal quantity, Bond bond) {
		super(purchaseDate, purchasePrice, quantity, bond);
	}

	@Override
	public void accept(TransactionVisitor visitor) throws InsufficientAssetsException {
		visitor.visit(this);
	}

}
