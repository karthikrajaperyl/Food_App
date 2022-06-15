import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

enum OfferType {
	AMOUNT, CARD;

	int amount;
	int percentage;
	Map<String, Integer> card = new HashMap<>();
}

public class Restaurants {
	// public long id;
	public String resturantName;
	public String address;
	public int offerId;
	public Performance restaurantPerformance;
	public List<Coupon> couponList = new ArrayList<>();
	protected static List<Food> restaurantFoodList = new ArrayList<>();
	protected static Map<Integer, Food> restaurantFoodMap = new HashMap<>();// foodId,FoodObj
	protected static List<ComboFood> restaurantComboList = new ArrayList<>();
	public OfferType offerType;

	Restaurants(String name, String address) {
		this.resturantName = name;
		this.address = address;
		Food_App.restaurantName.add(this.resturantName);
	}

	public void setRestaurantPerformance(Performance restaurantPerform) {
		this.restaurantPerformance = restaurantPerform;
	}

	protected void addFoodDetails() throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		Food foodObj = new Food();
		foodObj.setId(foodObj.count++);
		System.out.println("Enter the food name");
		String foodName = readerObj.readLine();
		foodObj.setName(foodName);
		System.out.println("please enter the price");
		Integer prize = Integer.parseInt(readerObj.readLine());
		foodObj.setPrize(prize);
		System.out.println("Enter the food is veg or non-veg 1/0");
		int i = Integer.parseInt(readerObj.readLine());
		boolean veg = (i == 1);
		foodObj.setVeg(veg);
		System.out.println("Please add the food ingredients with colon seperator (:)");
		String ingredients = readerObj.readLine();
		foodObj.setIngredients(ingredients);
		restaurantFoodList.add(foodObj);
		Food_App.foods.put(foodObj.getId(), foodObj);
		Food_App.availableFood.put(this.resturantName + "_" + foodObj.getId(), foodObj);
	}

	protected void addCombo(ComboFood foodObj) {
		restaurantComboList.add(foodObj);
	}

	@Override
	public String toString() {
		return "\nResturants Name" + this.resturantName + "\nAddress" + this.address
				+ "\ndiscount for above 1000 purchase" + this.offerType.percentage;
	}

	public void addOffer(int amount, Date expiry) {
		int couponId = Coupon.count++;
		Coupon coupon = new Coupon(couponId, amount, expiry);
		couponList.add(coupon);
	}

	protected List<Coupon> getCouponList() {
		return couponList;
	}

	public int getOfferId() {
		return offerId;
	}
}

class Performance {
	static int reviewIndex = -1;
	public Integer rating;
	public static String review;

	Performance(int rating, String reviewField) {
		this.rating = rating;
		this.review = reviewField;
	}
}