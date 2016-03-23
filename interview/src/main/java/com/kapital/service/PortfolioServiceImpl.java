package com.kapital.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.kapital.Currency;
import com.kapital.Portfolio;
import com.kapital.Price;
import com.kapital.assets.Asset;
import com.kapital.assets.Investment;

import java.util.Set;

public class PortfolioServiceImpl implements PortfolioService {

	PricingService pricingService;

	public PortfolioServiceImpl(PricingService pricingService){
		this.pricingService = pricingService;
	}
	
	@Override
	public Price getPriceInUsd(Portfolio portfolio) {
		return getPrice(portfolio, Currency.USD);
	}

	@Override
	public Price getPriceInEur(Portfolio portfolio) {
		return getPrice(portfolio, Currency.EUR);
	}

	@Override
	public Price getPrice(Portfolio portfolio, Currency currency) {
		Map<Asset, BigDecimal> quantityByAsset = portfolio.getQuantityByAssets();
		BigDecimal value = new BigDecimal("0");
		for (Entry<Asset, BigDecimal> entry : quantityByAsset.entrySet()) {
			value = value.add(entry.getValue().multiply(pricingService.getPrice(entry.getKey(), currency).getValue()));
		}
		return new Price(currency, value);
	}

	@Override
	public Map<Asset, Price> getPnL(Portfolio portfolio) {
		Map<Asset, Price> pnl = new HashMap<Asset, Price>();
		for(Entry<Asset, Investment> entry: portfolio.getInvestmentByAssets().entrySet() ){
			Asset asset = entry.getKey();
			pnl.put(asset, entry.getValue().getPnL(pricingService.getPrice(asset, Currency.USD)));
		}
		return pnl;
	}

	@Override
	public Map<Asset, Price> getRealizedPnL(Portfolio portfolio) {
		Map<Asset, Price> pnl = new HashMap<Asset, Price>();		
		for(Entry<Asset, Investment> entry: portfolio.getInvestmentByAssets().entrySet() ){
			Asset asset = entry.getKey();
			pnl.put(asset, entry.getValue().getRealizedPnL());
		}
		return pnl;
	}

	@Override
	public Map<Asset, Price> getUnrealizedPnL(Portfolio portfolio) {
		Map<Asset, Price> pnl = new HashMap<Asset, Price>();
		for(Entry<Asset, Investment> entry: portfolio.getInvestmentByAssets().entrySet() ){
			Asset asset = entry.getKey();
			pnl.put(asset, entry.getValue().getUnrealizedPnL(pricingService.getPrice(asset, Currency.USD)));
		}
		return pnl;
	}


}
