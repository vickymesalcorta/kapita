package com.kapital.assets;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.kapital.Issuer;

/* A bond is a loan to a company or government, with you as the bondholder being the lender.*/
public class Bond implements Asset{

	private final Issuer issuer;
	private final DateTime maturityDate;
	private final BigDecimal coupon;

	public Bond(Issuer issuer, DateTime maturityDate, BigDecimal coupon) {
		this.issuer = issuer;
		this.maturityDate = maturityDate;
		this.coupon = coupon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coupon == null) ? 0 : coupon.hashCode());
		result = prime * result + ((issuer == null) ? 0 : issuer.hashCode());
		result = prime * result + ((maturityDate == null) ? 0 : maturityDate.hashCode());
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
		Bond other = (Bond) obj;
		if (coupon == null) {
			if (other.coupon != null)
				return false;
		} else if (!coupon.equals(other.coupon))
			return false;
		if (issuer != other.issuer)
			return false;
		if (maturityDate == null) {
			if (other.maturityDate != null)
				return false;
		} else if (!maturityDate.equals(other.maturityDate))
			return false;
		return true;
	}

}
