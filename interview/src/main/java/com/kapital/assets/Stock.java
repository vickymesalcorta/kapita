package com.kapital.assets;

import com.kapital.Corporation;

/* A share of stock represents a percentage of ownership in a corporation */
public class Stock implements Asset {

	private final Corporation corporation;

	public Stock(Corporation corporation) {
		this.corporation = corporation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((corporation == null) ? 0 : corporation.hashCode());
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
		Stock other = (Stock) obj;
		if (corporation != other.corporation)
			return false;
		return true;
	}

}
