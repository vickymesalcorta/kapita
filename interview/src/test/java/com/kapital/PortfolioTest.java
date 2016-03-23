package com.kapital;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.kapital.Corporation;
import com.kapital.Currency;
import com.kapital.InsufficientAssetsException;
import com.kapital.Issuer;
import com.kapital.Portfolio;
import com.kapital.Price;
import com.kapital.assets.Bond;
import com.kapital.assets.Cash;
import com.kapital.assets.Stock;
import com.kapital.transactions.Transaction;
import com.kapital.transactions.bonds.BuyBondsTransaction;
import com.kapital.transactions.bonds.SellBondsTransaction;
import com.kapital.transactions.cash.BuyCashTransaction;
import com.kapital.transactions.cash.SellCashTransaction;
import com.kapital.transactions.stocks.BuyStocksTransaction;
import com.kapital.transactions.stocks.SellStocksTransaction;

import junit.framework.TestCase;

public class PortfolioTest extends TestCase {
	private Portfolio portfolio;
	private Price price;
	private BigDecimal bigDecimal;
	private Bond bond1;
	private Bond bond2;
	private Cash cash1;
	private Cash cash2;
	private Stock stock1;
	private Stock stock2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		portfolio = new Portfolio();
		price = new Price(Currency.USD, new BigDecimal("1"));
		bigDecimal = new BigDecimal("10");
		bond1 = new Bond(Issuer.ARG_GOVT, DateTime.now(), bigDecimal);
		bond2 = new Bond(Issuer.USA_GOVT, DateTime.now(), bigDecimal);
		cash1 = new Cash(Currency.EUR);
		cash2 = new Cash(Currency.USD);
		stock1 = new Stock(Corporation.BRITISH_AMERICAN_TOBACCO);
		stock2 = new Stock(Corporation.RIVIERA_HOLDINGS_CORPORATION);
	}

	@Override
	protected void tearDown() throws Exception {
		portfolio = null;
		price = null;
		bigDecimal = null;
		bond1 = null;
		bond2 = null;
		cash1 = null;
		cash2 = null;
		stock1 = null;
		stock2 = null;
	}
	
	@Test
	public void testLoadConsistentHistory() {
		List<Transaction> history = new ArrayList<Transaction>();
		/* All kinds of transactions in a consistent order */
		history.add(new BuyBondsTransaction(DateTime.now(), price, new BigDecimal("10"), bond1));
		history.add(new SellBondsTransaction(DateTime.now(), price, new BigDecimal("1"), bond1));
		history.add(new BuyBondsTransaction(DateTime.now(), price, new BigDecimal("10"), bond2));
		history.add(new BuyCashTransaction(DateTime.now(), price, new BigDecimal("10"), cash1));
		history.add(new SellCashTransaction(DateTime.now(), price, new BigDecimal("10"), cash1));
		history.add(new BuyStocksTransaction(DateTime.now(), price, new BigDecimal("10"), stock1));
		history.add(new SellStocksTransaction(DateTime.now(), price, new BigDecimal("10"), stock1));

		try {
			Portfolio portfolio = new Portfolio(history);
			assertEquals(2, portfolio.getBonds().entrySet().size());
			assertEquals(1, portfolio.getCash().entrySet().size());
			assertEquals(1, portfolio.getStocks().entrySet().size());
		} catch (InsufficientAssetsException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = InsufficientAssetsException.class)
	public void testLoadInconsistentHistory() {
		List<Transaction> history = new ArrayList<Transaction>();
		history.add(new SellBondsTransaction(DateTime.now(), price, new BigDecimal("1"), bond1));
		try {
			new Portfolio(history);
		} catch (InsufficientAssetsException e) {
		}
	}

	/* Bonds */
	@Test
	public void testApplyBuyBondsTransaction() {
		assertEquals(0, portfolio.getBonds().entrySet().size());
		BuyBondsTransaction transaction = new BuyBondsTransaction(DateTime.now(), price, bigDecimal, bond1);
		portfolio.applyBuyBondsTransaction(transaction);
		assertEquals(1, portfolio.getBonds().entrySet().size());
		/* Try to make another transaction with the same bond */
		portfolio.applyBuyBondsTransaction(transaction);
		assertEquals(1, portfolio.getBonds().entrySet().size());
	}

	@Test(expected = InsufficientAssetsException.class)
	public void testApplySellBondsTransactionWithInsufficientBond() {
		assertEquals(0, portfolio.getBonds().entrySet().size());
		SellBondsTransaction sellTransaction = new SellBondsTransaction(DateTime.now(), price, new BigDecimal("1"),
				bond1);
		try {
			portfolio.applySellBondsTransaction(sellTransaction);
		} catch (InsufficientAssetsException e) {
		}
	}

	@Test
	public void testApplySellBondsTransaction() {
		assertEquals(0, portfolio.getBonds().entrySet().size());
		BuyBondsTransaction buyTransaction = new BuyBondsTransaction(DateTime.now(), price, new BigDecimal("10"),
				bond1);
		SellBondsTransaction sellTransaction = new SellBondsTransaction(DateTime.now(), price, new BigDecimal("1"),
				bond1);
		portfolio.applyBuyBondsTransaction(buyTransaction);
		try {
			portfolio.applySellBondsTransaction(sellTransaction);
			assertEquals(0, new BigDecimal("9").compareTo(portfolio.getBonds().get(bond1).getHeldQuantity()));
		} catch (InsufficientAssetsException e) {
		}
	}

	/* Cash */
	@Test
	public void testApplyBuyCashTransaction() {
		assertEquals(0, portfolio.getCash().entrySet().size());
		BuyCashTransaction transaction = new BuyCashTransaction(DateTime.now(), price, bigDecimal, cash1);
		portfolio.applyBuyCashTransaction(transaction);
		assertEquals(1, portfolio.getCash().entrySet().size());
		/* Try to make another transaction with the same cash */
		portfolio.applyBuyCashTransaction(transaction);
		assertEquals(1, portfolio.getCash().entrySet().size());
	}

	@Test(expected = InsufficientAssetsException.class)
	public void testApplySellCashTransactionWithInsufficientCash() {
		assertEquals(0, portfolio.getCash().entrySet().size());
		SellCashTransaction sellTransaction = new SellCashTransaction(DateTime.now(), price, new BigDecimal("1"),
				cash1);
		try {
			portfolio.applySellCashTransaction(sellTransaction);
		} catch (InsufficientAssetsException e) {
		}
	}

	@Test
	public void testApplySellCashTransaction() {
		assertEquals(0, portfolio.getCash().entrySet().size());
		BuyCashTransaction buyTransaction = new BuyCashTransaction(DateTime.now(), price, new BigDecimal("10"),
				cash1);
		SellCashTransaction sellTransaction = new SellCashTransaction(DateTime.now(), price, new BigDecimal("1"),
				cash1);
		portfolio.applyBuyCashTransaction(buyTransaction);
		try {
			portfolio.applySellCashTransaction(sellTransaction);
			assertEquals(0, new BigDecimal("9").compareTo(portfolio.getCash().get(cash1).getHeldQuantity()));
		} catch (InsufficientAssetsException e) {
		}
	}
	
	/* Stocks */
	@Test
	public void testApplyBuyStocksTransaction() {
		assertEquals(0, portfolio.getStocks().entrySet().size());
		BuyStocksTransaction transaction = new BuyStocksTransaction(DateTime.now(), price, bigDecimal, stock1);
		portfolio.applyBuyStocksTransaction(transaction);
		assertEquals(1, portfolio.getStocks().entrySet().size());
		/* Try to make another transaction with the same cash */
		portfolio.applyBuyStocksTransaction(transaction);
		assertEquals(1, portfolio.getStocks().entrySet().size());
	}

	@Test(expected = InsufficientAssetsException.class)
	public void testApplySellStocksTransactionWithInsufficientStocks() {
		assertEquals(0, portfolio.getStocks().entrySet().size());
		SellStocksTransaction sellTransaction = new SellStocksTransaction(DateTime.now(), price, new BigDecimal("1"),
				stock1);
		try {
			portfolio.applySellStocksTransaction(sellTransaction);
		} catch (InsufficientAssetsException e) {
		}
	}

	@Test
	public void testApplySellStocksTransaction() {
		assertEquals(0, portfolio.getStocks().entrySet().size());
		BuyStocksTransaction buyTransaction = new BuyStocksTransaction(DateTime.now(), price, new BigDecimal("10"),
				stock1);
		SellStocksTransaction sellTransaction = new SellStocksTransaction(DateTime.now(), price, new BigDecimal("1"),
				stock1);
		portfolio.applyBuyStocksTransaction(buyTransaction);
		try {
			portfolio.applySellStocksTransaction(sellTransaction);
			assertEquals(0, new BigDecimal("9").compareTo(portfolio.getStocks().get(stock1).getHeldQuantity()));
		} catch (InsufficientAssetsException e) {
		}
	}
}
