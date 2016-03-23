package com.kapital.service;

import java.util.Map;

import com.kapital.Currency;
import com.kapital.Portfolio;
import com.kapital.Price;
import com.kapital.assets.Asset;

public interface PortfolioService {
	
	public Price getPriceInUsd(Portfolio portfolio);

	public Price getPriceInEur(Portfolio portfolio);
	
	public Price getPrice(Portfolio portfolio, Currency currency);
	
	public Map<Asset, Price> getPnL(Portfolio portfolio);

	public Map<Asset, Price> getRealizedPnL(Portfolio portfolio);
	
	public Map<Asset, Price> getUnrealizedPnL(Portfolio portfolio);
}
