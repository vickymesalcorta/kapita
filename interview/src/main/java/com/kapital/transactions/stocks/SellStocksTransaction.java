package com.kapital.transactions.stocks;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.InsufficientAssetsException;
import com.kapital.Price;
import com.kapital.assets.Stock;
import com.kapital.transactions.TransactionVisitor;

public class SellStocksTransaction extends StocksTransaction {

	public SellStocksTransaction(DateTime purchaseDate, Price purchasePrice, BigDecimal quantity, Stock stock) {
		super(purchaseDate, purchasePrice, quantity, stock);
	}

	@Override
	public void accept(TransactionVisitor visitor) throws InsufficientAssetsException {
		visitor.visit(this);
	}
}
