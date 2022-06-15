import java.util.*;

public class Order {
	private static final String name = "raja";
	private static final String address = "abz";
	private String foodName;
	private int foodId;
	private String restaurantName;
	public boolean hasOffer;
	private static int totalAmount;
	// private Banking userBanking;
	private static List<Order> orderList = new ArrayList<>();
	public int amount;
	protected static List<Integer> couponList = new ArrayList<>();

	protected void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	protected int getFoodId() {
		return foodId;
	}

	protected void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	protected String getFoodName() {
		return foodName;
	}

	protected void setRestaurantName(String name) {
		this.restaurantName = name;
	}

	protected String getRestaurantName() {
		return restaurantName;
	}

	protected String getName() {
		return name;
	}

	protected String getAddress() {
		return address;
	}

	protected void addCoupon(int id) {
		couponList.add(id);
	}

	protected static void setTotalAmount(int amount) {
		Order.totalAmount = amount;
	}

	protected static int getTotalAmount() {
		return Order.totalAmount;
	}

	protected static void setOrderList(List<Order> orderList) {
		Order.orderList = orderList;
	}

	protected static List<Order> getOrderList() {
		return Order.orderList;
	}
	/*
	 * protected void userBanking(Banking userbanking) { this.userBanking =
	 * userbanking; }
	 * 
	 * protected Banking userBanking() { return userBanking; }
	 */

	protected void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return ("RestaurantName - " + restaurantName + " " + " Food Name " + foodName + " amount " + Order.totalAmount);
	}
}
