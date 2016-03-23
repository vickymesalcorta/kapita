package com.kapital.transactions.stocks;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.Price;
import com.kapital.assets.Stock;
import com.kapital.transactions.TransactionVisitor;

public class BuyStocksTransaction extends StocksTransaction{
	
	public BuyStocksTransaction(DateTime transactionDate, Price transactionPrice, BigDecimal quantity, Stock stock) {
		super(transactionDate, transactionPrice, quantity, stock);
	}

	@Override
	public void accept(TransactionVisitor visitor) {
		visitor.visit(this);
	}

}
