package com.kapital.assets;

import java.math.BigDecimal;
import java.math.MathContext;

import com.kapital.Currency;
import com.kapital.Price;
import com.kapital.transactions.Transaction;

/* All the investments are considered analyzed in USD */
public class Investment {
	private BigDecimal boughtQuantity = new BigDecimal("0");
	private BigDecimal cost = new BigDecimal("0");
	private BigDecimal soldQuantity = new BigDecimal("0");
	private BigDecimal earnings = new BigDecimal("0");
	private final BigDecimal ZERO = new BigDecimal("0");

	public Investment() {
	}

	public BigDecimal getHeldQuantity() {
		return boughtQuantity.subtract(soldQuantity);
	}

	public void loadBuyTransaction(Transaction transaction) {
		cost = cost.add(transaction.getCost());
		boughtQuantity = boughtQuantity.add(transaction.getQuantity());
		cost = cost.add(transaction.getQuantity().multiply(transaction.getPrice().getValue()));
	}

	public void loadSellTransaction(Transaction transaction) {
		cost = cost.add(transaction.getCost());
		soldQuantity = soldQuantity.add(transaction.getQuantity());
		earnings = earnings.add(transaction.getQuantity().multiply(transaction.getPrice().getValue()));
	}

	public Price getRealizedPnL() {
		BigDecimal pnl = soldQuantity.multiply(getUnitaryEarnings().subtract(getUnitaryCost()));
		return new Price(Currency.USD, pnl);
	}

	public Price getUnrealizedPnL(Price currentPrice) {
		BigDecimal pnl = getHeldQuantity().multiply(currentPrice.getValue().subtract(getUnitaryCost()));
		return new Price(Currency.USD, pnl);
	}

	public Price getPnL(Price currentPrice) {
		BigDecimal pnl = getRealizedPnL().getValue().add(getUnrealizedPnL(currentPrice).getValue());
		return new Price(Currency.USD, pnl);
	}

	public BigDecimal getUnitaryCost() {
		if (boughtQuantity.compareTo(ZERO) == 0) {
			return ZERO;
		}
		return cost.divide(boughtQuantity, MathContext.DECIMAL128);
	}

	public BigDecimal getUnitaryEarnings() {
		if (soldQuantity.compareTo(ZERO) == 0) {
			return ZERO;
		}
		return earnings.divide(soldQuantity, MathContext.DECIMAL128);
	}

	public BigDecimal getBoughtQuantity() {
		return boughtQuantity;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public BigDecimal getSoldQuantity() {
		return soldQuantity;
	}

	public BigDecimal getEarnings() {
		return earnings;
	}

}
