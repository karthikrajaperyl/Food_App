import java.util.*;

public class FoodCart {
	private static int totalAmount;
	private Order orderObj;
	protected Map<String, List<Integer>> cartMap = new HashMap<>();// resname.foodIds

	protected Order getUserInstance() {
		return this.orderObj;
	}

	public void setUserInstance() {
		orderObj = new Order();
	}

	protected void addToCart(String restaurantName, int foodId) {
		if (cartMap.get(restaurantName) != null) {
			cartMap.get(restaurantName).add(foodId);
		} else {
			ArrayList<Integer> foodList = new ArrayList<>();
			foodList.add(foodId);
			cartMap.put(restaurantName, foodList);
		}
		updateAmount(Food_App.availableFood.get(restaurantName + "_" + foodId).getPrize());
		orderObj.setFoodId(foodId);
		orderObj.setRestaurantName(restaurantName);
	}

	private void updateAmount(int amount) {
		totalAmount += amount;
	}

	protected Map<String, List<Integer>> getCartStorage() {
		return cartMap;
	}

	protected int getAmount() {
		return totalAmount;
	}
}
