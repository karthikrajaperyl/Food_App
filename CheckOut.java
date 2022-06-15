import java.util.*;

public class CheckOut {
	protected int amount;
	protected List<Food> foodObj = new ArrayList<>();
	protected List<Order> orderList = new ArrayList<>();
	protected static int count;
	protected static Map<Integer, CheckOut> historyStorage = new HashMap<>();

	protected void setSelectedItem(List<Food> foodObj) {
		this.foodObj = foodObj;
	}

	/*
	 * protected boolean applyCoupon(int couponId) { if (CheckIdStatus(couponId)) {
	 * this.amount = amount -
	 * Food_App.restaurantsStorage.get(userObj.getRestaurantName()).couponList.get(
	 * couponId).getAmount();
	 * couponUsed.add(Food_App.restaurantsStorage.get(userObj.getRestaurantName()).
	 * couponList.get(couponId)); return true; } else return false; }
	 */
	protected void setTotalAmount(int amount) {
		this.amount = amount;
	}

	protected int getTotalAmount() {
		return amount;
	}

	protected void applyDiscount(int discount) {
		this.amount = this.getTotalAmount();
		amount -= amount * (discount / 100);
		setTotalAmount(amount);

	}

	/*
	 * protected void storeHistory() { Print printObj=new Print();
	 * printObj.printOrderHistoryToFile(userObj); }
	 */

	protected void applyTax() {
		amount = (amount + amount * 10 / 100);
		setTotalAmount(amount);

	}

	protected CheckOut getInstance() {
		return this;
	}

	protected void printOrder() {
		Print printObj = new Print();
		for (Order iterList : Order.getOrderList()) {
			printObj.printOrderHistoryToFile(iterList);
		}
	}
}

class checkOutHistory extends CheckOut {
	private boolean couponApplied;
	CheckOut checkObj = getInstance();
	int i = CheckOut.count;
	// CheckOut.historyStorage.put(i,checkObj);
}