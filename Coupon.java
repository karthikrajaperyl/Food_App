import java.util.*;

public class Coupon {
	static int count = 0;
	private int couponId;
	private int amount;
	private Date ExpiryTime;

	Coupon(int couponId, int amount, Date expiryTime) {
		this.couponId = couponId;
		this.amount = amount;
		this.ExpiryTime = expiryTime;
	}


	protected int getCouponId() {
		return couponId;
	}

	protected int getAmount() {
		return amount;
	}

	protected Date getExpiryTime() {
		return ExpiryTime;
	}

}
