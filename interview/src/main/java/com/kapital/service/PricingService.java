package com.kapital.service;

import com.kapital.Currency;
import com.kapital.Price;
import com.kapital.assets.Asset;

public interface PricingService {
	public Price getPrice(Asset asset, Currency currency);
}
