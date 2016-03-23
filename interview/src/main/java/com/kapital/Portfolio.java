package com.kapital;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kapital.assets.Asset;
import com.kapital.assets.Bond;
import com.kapital.assets.Cash;
import com.kapital.assets.Investment;
import com.kapital.assets.Stock;
import com.kapital.transactions.Transaction;
import com.kapital.transactions.TransactionVisitor;
import com.kapital.transactions.bonds.BuyBondsTransaction;
import com.kapital.transactions.bonds.SellBondsTransaction;
import com.kapital.transactions.cash.BuyCashTransaction;
import com.kapital.transactions.cash.SellCashTransaction;
import com.kapital.transactions.stocks.BuyStocksTransaction;
import com.kapital.transactions.stocks.SellStocksTransaction;

public class Portfolio {

	private Map<Bond, Investment> bonds = new HashMap<Bond, Investment>();
	private Map<Cash, Investment> cash = new HashMap<Cash, Investment>();
	private Map<Stock, Investment> stocks = new HashMap<Stock, Investment>();

	public Portfolio(List<Transaction> history) throws InsufficientAssetsException {
		this.loadTransactions(history);
	}

	public Portfolio() throws InsufficientAssetsException {
	}

	private void loadTransactions(List<Transaction> history) throws InsufficientAssetsException {
		TransactionVisitor visitor = new TransactionVisitorImpl();
		for (Transaction transaction : history) {
			transaction.accept(visitor);
		}
	}

	public void applyBuyBondsTransaction(BuyBondsTransaction transaction) {
		Bond bond = transaction.getBond();
		bonds.putIfAbsent(bond, new Investment());
		bonds.get(bond).loadBuyTransaction(transaction);
	}

	public void applySellBondsTransaction(SellBondsTransaction transaction) throws InsufficientAssetsException {
		Bond bond = transaction.getBond();
		Investment investment = bonds.putIfAbsent(bond, new Investment());
		if (investment != null) {
			checkAndLoadSellTransaction(investment, transaction);
		} else {
			throw new InsufficientAssetsException();
		}
	}

	public void applyBuyCashTransaction(BuyCashTransaction transaction) {
		Cash cashAux = transaction.getCash();
		cash.putIfAbsent(cashAux, new Investment());
		cash.get(cashAux).loadBuyTransaction(transaction);
	}

	public void applySellCashTransaction(SellCashTransaction transaction) throws InsufficientAssetsException {
		Cash cashAux = transaction.getCash();
		Investment investment = cash.putIfAbsent(cashAux, new Investment());
		if (investment != null) {
			checkAndLoadSellTransaction(investment, transaction);
		} else {
			throw new InsufficientAssetsException();
		}
	}

	public void applyBuyStocksTransaction(BuyStocksTransaction transaction) {
		Stock stock = transaction.getStock();
		stocks.putIfAbsent(stock, new Investment());
		stocks.get(stock).loadBuyTransaction(transaction);
	}

	public void applySellStocksTransaction(SellStocksTransaction transaction) throws InsufficientAssetsException {
		Stock stock = transaction.getStock();
		Investment investment = stocks.putIfAbsent(stock, new Investment());
		if (investment != null) {
			checkAndLoadSellTransaction(investment, transaction);
		} else {
			throw new InsufficientAssetsException();
		}
	}

	private void checkAndLoadSellTransaction(Investment investment, Transaction transaction)
			throws InsufficientAssetsException {
		int difference = investment.getHeldQuantity().compareTo(transaction.getQuantity());
		if (difference < 0) {
			throw new InsufficientAssetsException();
		} else {
			investment.loadSellTransaction(transaction);
		}
	}

	public Map<Asset, BigDecimal> getQuantityByAssets() {
		Map<Asset, BigDecimal> quantityByAssets = new HashMap<Asset, BigDecimal>();
		loadToQuantityByAssetMap(bonds, quantityByAssets);
		loadToQuantityByAssetMap(cash, quantityByAssets);
		loadToQuantityByAssetMap(stocks, quantityByAssets);
		return quantityByAssets;
	}

	private void loadToQuantityByAssetMap(Map<? extends Asset, Investment> toLoad,
			Map<Asset, BigDecimal> quantityByAsset) {
		for (Entry<? extends Asset, Investment> entry : toLoad.entrySet()) {
			quantityByAsset.put(entry.getKey(), entry.getValue().getHeldQuantity());
		}
	}

	public Map<Asset, Investment> getInvestmentByAssets() {
		Map<Asset, Investment> investmentByAsset = new HashMap<Asset, Investment>();
		investmentByAsset.putAll(bonds);
		investmentByAsset.putAll(cash);
		investmentByAsset.putAll(stocks);
		return investmentByAsset;
	}

	public Map<Bond, Investment> getBonds() {
		return bonds;
	}

	public Map<Cash, Investment> getCash() {
		return cash;
	}

	public Map<Stock, Investment> getStocks() {
		return stocks;
	}

	private class TransactionVisitorImpl implements TransactionVisitor {

		@Override
		public void visit(BuyBondsTransaction transaction) {
			applyBuyBondsTransaction(transaction);
		}

		@Override
		public void visit(SellBondsTransaction transaction) throws InsufficientAssetsException {
			applySellBondsTransaction(transaction);
		}

		@Override
		public void visit(BuyCashTransaction transaction) {
			applyBuyCashTransaction(transaction);
		}

		@Override
		public void visit(SellCashTransaction transaction) throws InsufficientAssetsException {
			applySellCashTransaction(transaction);
		}

		@Override
		public void visit(BuyStocksTransaction transaction) {
			applyBuyStocksTransaction(transaction);
		}

		@Override
		public void visit(SellStocksTransaction transaction) throws InsufficientAssetsException {
			applySellStocksTransaction(transaction);
		}
	}
}
