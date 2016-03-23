package com.kapital.transactions.cash;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.Price;
import com.kapital.assets.Cash;
import com.kapital.transactions.TransactionVisitor;

public class BuyCashTransaction extends CashTransaction{

	public BuyCashTransaction(DateTime date, Price price, BigDecimal quantity, Cash cash) {
		super(date, price, quantity, cash);
	}

	@Override
	public void accept(TransactionVisitor visitor) {
		visitor.visit(this);	
	}
	
}
