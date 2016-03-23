package com.kapital;

import java.math.BigDecimal;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import com.kapital.Corporation;
import com.kapital.Currency;
import com.kapital.Issuer;
import com.kapital.Portfolio;
import com.kapital.Price;
import com.kapital.assets.Asset;
import com.kapital.assets.Bond;
import com.kapital.assets.Cash;
import com.kapital.assets.Stock;
import com.kapital.service.PortfolioService;
import com.kapital.service.PortfolioServiceImpl;
import com.kapital.service.PricingService;
import com.kapital.transactions.bonds.BuyBondsTransaction;
import com.kapital.transactions.cash.BuyCashTransaction;
import com.kapital.transactions.stocks.BuyStocksTransaction;

import junit.framework.TestCase;

public class PortfolioServiceImplTest extends TestCase {

	private PricingService pricingService;
	private PortfolioService portfolioService;
	private Portfolio portfolio;
	private Price somePrice;
	private Bond bond1;
	private Bond bond2;
	private Cash cash1;
	private Cash cash2;
	private Stock stock1;
	private Stock stock2;

	/* Buy 10 bonds of $10 */
	private BuyBondsTransaction transaction1;
	private BigDecimal quantity1;

	/* Buy 5 bonds $11 */
	private BuyBondsTransaction transaction2;
	private BigDecimal quantity2;

	/* Buy 5 USD $1 */
	private BuyCashTransaction transaction3;
	private BigDecimal quantity3;

	/* Buy 5 EUR bonds $2 */
	private BuyCashTransaction transaction4;
	private BigDecimal quantity4;

	/* Buy 20 stocks of $15 */
	private BuyStocksTransaction transaction5;
	private BigDecimal quantity5;

	/* Buy 7 stocks $17 */
	private BuyStocksTransaction transaction6;
	private BigDecimal quantity6;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		pricingService = new MockPricingService();
		portfolioService = new PortfolioServiceImpl(pricingService);
		portfolio = new Portfolio();

		somePrice = new Price(Currency.USD, new BigDecimal("20"));
		bond1 = new Bond(Issuer.ARG_GOVT, null, new BigDecimal("0"));
		bond2 = new Bond(Issuer.USA_GOVT, null, new BigDecimal("0"));
		cash1 = new Cash(Currency.EUR);
		cash2 = new Cash(Currency.USD);
		stock1 = new Stock(Corporation.BRITISH_AMERICAN_TOBACCO);
		stock2 = new Stock(Corporation.RIVIERA_HOLDINGS_CORPORATION);

		quantity1 = new BigDecimal("10");
		transaction1 = new BuyBondsTransaction(DateTime.now(), somePrice, quantity1, bond1);

		quantity2 = new BigDecimal("5");
		transaction2 = new BuyBondsTransaction(DateTime.now(), somePrice, quantity2, bond2);

		quantity3 = new BigDecimal("5");
		transaction3 = new BuyCashTransaction(DateTime.now(), somePrice, quantity3, cash1);

		quantity4 = new BigDecimal("5");
		transaction4 = new BuyCashTransaction(DateTime.now(), somePrice, quantity4, cash2);

		quantity5 = new BigDecimal("20");
		transaction5 = new BuyStocksTransaction(DateTime.now(), somePrice, quantity5, stock1);

		quantity6 = new BigDecimal("7");
		transaction6 = new BuyStocksTransaction(DateTime.now(), somePrice, quantity6, stock2);

		portfolio.applyBuyBondsTransaction(transaction1);
		portfolio.applyBuyBondsTransaction(transaction2);
		portfolio.applyBuyCashTransaction(transaction3);
		portfolio.applyBuyCashTransaction(transaction4);
		portfolio.applyBuyStocksTransaction(transaction5);
		portfolio.applyBuyStocksTransaction(transaction6);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		//TODO
	}

	@Test
	public void testGetPrice() {
		Price price = portfolioService.getPrice(portfolio, Currency.USD);
		BigDecimal myPrice = new BigDecimal("589");
		assertEquals(Currency.USD, price.getCurrency());
		assertEquals(0, myPrice.compareTo(price.getValue()));
	}
	
	@Test
	public void testGetRealizedPnL(){
		Map<Asset, Price> pnl = portfolioService.getRealizedPnL(portfolio);
		assertEquals(6, pnl.entrySet().size());
	}
	
	@Test
	public void testGetUnrealizedPnL(){
		Map<Asset, Price> pnl = portfolioService.getUnrealizedPnL(portfolio);
		assertEquals(6, pnl.entrySet().size());
	}
	
	@Test
	public void testGetPnL(){
		Map<Asset, Price> pnl = portfolioService.getPnL(portfolio);
		assertEquals(6, pnl.entrySet().size());
	}
}
