package com.kapital;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.kapital.Corporation;
import com.kapital.Currency;
import com.kapital.Issuer;
import com.kapital.Price;
import com.kapital.assets.Asset;
import com.kapital.assets.Bond;
import com.kapital.assets.Cash;
import com.kapital.assets.Stock;
import com.kapital.service.PricingService;

public class MockPricingService implements PricingService {

	private Map<Asset, Price> pricesByAsset;

	public MockPricingService() {
		pricesByAsset = new HashMap<Asset, Price>();
		Bond bond1 = new Bond(Issuer.ARG_GOVT, null, new BigDecimal("0"));
		Bond bond2 = new Bond(Issuer.USA_GOVT, null, new BigDecimal("0"));
		Cash cash1 = new Cash(Currency.EUR);
		Cash cash2 = new Cash(Currency.USD);
		Stock stock1 = new Stock(Corporation.BRITISH_AMERICAN_TOBACCO);
		Stock stock2 = new Stock(Corporation.RIVIERA_HOLDINGS_CORPORATION);
		
		Price price1 = new Price(Currency.USD, new BigDecimal("10"));
		Price price2 = new Price(Currency.USD, new BigDecimal("11"));
		Price price3 = new Price(Currency.USD, new BigDecimal("1"));
		Price price4 = new Price(Currency.USD, new BigDecimal("2"));
		Price price5 = new Price(Currency.USD, new BigDecimal("15"));
		Price price6 = new Price(Currency.USD, new BigDecimal("17"));
		
		pricesByAsset.put(bond1, price1);
		pricesByAsset.put(bond2, price2);
		pricesByAsset.put(cash1, price3);
		pricesByAsset.put(cash2, price4);
		pricesByAsset.put(stock1, price5);
		pricesByAsset.put(stock2, price6);
	}

	@Override
	public Price getPrice(Asset asset, Currency currency) {
		if(pricesByAsset.containsKey(asset)){
			return pricesByAsset.get(asset);
		}
		return new Price(null, null);
	}
}
