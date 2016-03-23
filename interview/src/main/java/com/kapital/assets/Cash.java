package com.kapital.assets;

import com.kapital.Currency;

public class Cash implements Asset{
	
	private final Currency currency;

	public Cash(Currency currency){
		this.currency = currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cash other = (Cash) obj;
		if (currency != other.currency)
			return false;
		return true;
	}
	
	
}
