package com.kapital.transactions;


import com.kapital.InsufficientAssetsException;
import com.kapital.transactions.bonds.BuyBondsTransaction;
import com.kapital.transactions.bonds.SellBondsTransaction;
import com.kapital.transactions.cash.BuyCashTransaction;
import com.kapital.transactions.cash.SellCashTransaction;
import com.kapital.transactions.stocks.BuyStocksTransaction;
import com.kapital.transactions.stocks.SellStocksTransaction;

public interface TransactionVisitor {
	public void visit(BuyBondsTransaction transaction);
	public void visit(SellBondsTransaction transaction) throws InsufficientAssetsException;
	public void visit(BuyStocksTransaction transaction);
	public void visit(SellStocksTransaction transaction) throws InsufficientAssetsException;
	public void visit(BuyCashTransaction transaction);
	public void visit(SellCashTransaction transaction) throws InsufficientAssetsException;
}
