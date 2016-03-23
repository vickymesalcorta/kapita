package com.kapital;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;

import com.kapital.Currency;
import com.kapital.Price;
import com.kapital.assets.Bond;
import com.kapital.transactions.bonds.BondsTransaction;
import com.kapital.transactions.bonds.BuyBondsTransaction;

import junit.framework.TestCase;

public class BondsTransactionTest extends TestCase {

	private BondsTransaction transaction;
	private Price price;
	private BigDecimal quantity;
	private BigDecimal expectedCost;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		price = new Price(Currency.USD, new BigDecimal("100"));
		quantity = new BigDecimal("5");
		expectedCost = new BigDecimal("0.05");
		transaction = new BuyBondsTransaction(DateTime.now(), price, quantity, Mockito.mock(Bond.class));
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		transaction = null;
		price = null;
		quantity = null;
		expectedCost = null;
	}

	@Test
	public void testGetCost() {
		assertEquals(0, expectedCost.compareTo(transaction.getCost()));
	}
}
