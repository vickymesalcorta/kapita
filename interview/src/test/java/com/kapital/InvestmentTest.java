package com.kapital;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;

import com.kapital.Currency;
import com.kapital.Price;
import com.kapital.assets.Investment;
import com.kapital.assets.Stock;
import com.kapital.transactions.stocks.BuyStocksTransaction;
import com.kapital.transactions.stocks.SellStocksTransaction;

import junit.framework.TestCase;

public class InvestmentTest extends TestCase {

	private Investment investment;

	/* currentPrice $20 */
	private Price currentPrice;

	/* Buy 20 stocks of $15 */
	private BuyStocksTransaction transaction1;
	private Price price1;
	private BigDecimal quantity1;

	/* Sell 7 stocks $17 */
	private SellStocksTransaction transaction2;
	private Price price2;
	private BigDecimal quantity2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		investment = new Investment();
		currentPrice = new Price(Currency.USD, new BigDecimal("20"));

		quantity1 = new BigDecimal("20");
		price1 = new Price(Currency.USD, new BigDecimal("15"));
		transaction1 = new BuyStocksTransaction(DateTime.now(), price1, quantity1, Mockito.mock(Stock.class));

		quantity2 = new BigDecimal("7");
		price2 = new Price(Currency.USD, new BigDecimal("17"));
		transaction2 = new SellStocksTransaction(DateTime.now(), price2, quantity2, Mockito.mock(Stock.class));
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		investment = null;
		currentPrice = null;
		transaction1 = null;
		price1 = null;
		quantity1 = null;
		transaction2 = null;
		price2 = null;
		quantity2 = null;
	};

	@Test
	public void testGetHeldQuantity() {
		investment.loadBuyTransaction(transaction1);
		assertEquals(0, investment.getHeldQuantity().compareTo(quantity1));
		investment.loadBuyTransaction(transaction1);
		assertEquals(0, investment.getHeldQuantity().compareTo(quantity1.add(quantity1)));
		investment.loadSellTransaction(transaction2);
		assertEquals(0, investment.getHeldQuantity().compareTo(quantity1.add(quantity1).subtract(quantity2)));
	}

	@Test
	public void testLoadBuyTransaction() {
		investment.loadBuyTransaction(transaction1);
		BigDecimal cost = new BigDecimal("305");
		assertEquals(0, investment.getBoughtQuantity().compareTo(quantity1));
		assertEquals(0, investment.getCost().compareTo(cost));
		assertEquals(0, investment.getSoldQuantity().compareTo(new BigDecimal("0")));
		assertEquals(0, investment.getEarnings().compareTo(new BigDecimal("0")));
	}

	@Test
	public void testLoadSellTransaction() {
		investment.loadBuyTransaction(transaction1);
		investment.loadSellTransaction(transaction2);
		BigDecimal cost = new BigDecimal("310");
		BigDecimal earnings = new BigDecimal("119");
		assertEquals(0, investment.getBoughtQuantity().compareTo(quantity1));
		assertEquals(0, investment.getCost().compareTo(cost));
		assertEquals(0, investment.getSoldQuantity().compareTo(quantity2));
		assertEquals(0, investment.getEarnings().compareTo(earnings));
	}

//	@Test
//	public void testGetRealizedPnL() {
//		investment.loadBuyTransaction(transaction1);
//		investment.loadSellTransaction(transaction2);
//		/* soldQuantity * (unitaryEarnings - unitaryCost)
//		 * 		7	*	((119/7) - (310/20)) = 10.5
//		 */
//		BigDecimal pnl = new BigDecimal("10.5");
//		assertEquals(0, pnl.compareTo(investment.getRealizedPnL().getValue()));		
//	}
	
	@Test
	public void testGetUnrealizedPnL() {
		investment.loadBuyTransaction(transaction1);
		investment.loadSellTransaction(transaction2);
		/* heldQuantity * (currentPrice - unitaryCost)
		 * 		13	*	(20 - (310/20)) = 58.5
		 */
		BigDecimal pnl = new BigDecimal("58.5");
		System.out.println(pnl);
		System.out.println(investment.getUnrealizedPnL(currentPrice).getValue());
		assertEquals(0, pnl.compareTo(investment.getUnrealizedPnL(currentPrice).getValue()));		
	}

//	@Test
//	public void testGetPnL(){
//		investment.loadBuyTransaction(transaction1);
//		investment.loadSellTransaction(transaction2);
//		/* pnl = unrealizedPnL + realizedPnL */
//		BigDecimal pnl = new BigDecimal("69");
//		assertEquals(0, pnl.compareTo(investment.getPnL(currentPrice).getValue()));
//	}
}
