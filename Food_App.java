import java.io.*;
import java.util.*;
import java.text.*;

public class Food_App {
	protected static Map<String, Restaurants> restaurantsStorage = new HashMap<>();// resname,restobj
	protected static List<Restaurants> restaurantsList = new ArrayList<>();
	protected static Map<String, Food> availableFood = new HashMap<>();// resname_foodid,foodObj
	protected static Map<Integer, List<Restaurants>> foodWithRestaurants = new TreeMap<>();//
	protected static List<String> restaurantName = new ArrayList<>();

	// protected static List<FoodCart> cartList = new ArrayList<>();// user food
	// cart
	protected static Map<Integer, Food> foods = new HashMap<>();

	protected void addFoodWithRestaurants(int foodId, Restaurants restaurantsObj) {
		if (foodWithRestaurants.get(foodId) == null) {
			foodWithRestaurants.get(foodId).add(restaurantsObj);
		} else {
			List<Restaurants> restaurantList = new ArrayList<>();
			restaurantList.add(restaurantsObj);
			foodWithRestaurants.put(foodId, restaurantsList);
		}
	}

	protected Performance getRestaurantPerformance() throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Star rating");
		int rating = Integer.parseInt(readerObj.readLine());
		System.out.println("Enter a review");
		String review = readerObj.readLine();
		Performance tempObj = new Performance(rating, review);
		return tempObj;
	}

	protected void addRestaurentDetails() throws IOException, Exception {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the Restuarant Name");
		String restaurantName = readerObj.readLine();
		System.out.println("Enter the Restaurant Address");
		String address = readerObj.readLine();
		Restaurants restaurantObj = new Restaurants(restaurantName, address);
		boolean continuation = true;
		int i;
		while (continuation) {
			addFoodToRestaurants(restaurantObj);
			System.out.println("Do you want to add the food- 1/0");
			i = Integer.parseInt(readerObj.readLine());
			if (i == 0) {
				continuation = false;
			}
		}
		addOffer(restaurantObj);
		restaurantsStorage.put(restaurantName, restaurantObj);
		restaurantsList.add(restaurantObj);
	}

	protected void addFoodToRestaurants(Restaurants restaurantsObj) throws IOException {
		restaurantsObj.addFoodDetails();

	}

	protected void addOffer(Restaurants restaurantObj) throws Exception {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(
				"please prefer the offer Type  1.all customer with minimum amount\n2.offer with card \n3.no Offer");

		int choice = Integer.parseInt(readerObj.readLine());
		if (choice == 1) {
			restaurantObj.offerType = OfferType.AMOUNT;
			System.out.println("Please enter the minimum amount to get offer");
			restaurantObj.offerType.amount = Integer.parseInt(readerObj.readLine());
			System.out.println("Please enter your percentage amount");
			restaurantObj.offerType.percentage = Integer.parseInt(readerObj.readLine());
			System.out.println("Offer added successfully");

		} else if (choice == 2) {
			int ch = 1;
			restaurantObj.offerType = OfferType.CARD;
			while (ch == 1) {
				System.out.println("\ndo you want to add the card details please enter \n0.break 1.continue");
				ch = Integer.parseInt(readerObj.readLine());
				if (ch == 0) {
					break;
				}
				System.out.println("enter the card name ");
				String cardName = readerObj.readLine();
				System.out.println("enter the offer percentage");
				int percentage = Integer.parseInt(readerObj.readLine());
				restaurantObj.offerType.card.put(cardName, percentage);
			}
			System.out.println("Offer added successfully");
		}

		System.out.println("1.coupon with expiry \n2.coupon with no expiry\n3.no coupon");
		choice = Integer.parseInt(readerObj.readLine());
		if (choice == 1) {
			System.out.println("Enter the Offer amount");
			int amount = Integer.parseInt(readerObj.readLine());
			System.out.println("Enter the Expiry Time in format dd/MM/yyyy HH:mm:ss");
			String date1 = readerObj.readLine();
			Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date1);
			restaurantObj.addOffer(amount, date);
			System.out.println("coupon added Successfully");
		} else if (choice == 2) {
			System.out.println("Enter the Offer amount");
			int amount = Integer.parseInt(readerObj.readLine());
			restaurantObj.addOffer(amount, null);
			System.out.println("coupon added Successfully");
		}

	}

	protected int setOfferType(Restaurants restaurantObj, int amount) throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		if (restaurantObj.offerType != null) {
			OfferType offer = restaurantObj.offerType;
			switch (offer) {
			case AMOUNT:
				if (amount >= offer.amount) {
					amount = amount - (amount * offer.percentage / 100);
				}
				break;
			case CARD:
				System.out.println("do you want to use card payment 1.yes 2.no");
				if (Integer.parseInt(readerObj.readLine()) == 1) {
					System.out.println("please enter your card");
					int percentage = offer.card.get(readerObj.readLine());
					amount = amount - (amount * percentage / 100);
				}
				break;
			}
		}

		System.out.println("do you have the coupon 1.yes 2.no");

		if (Integer.parseInt(readerObj.readLine()) == 1) {
			System.out.println("please add your coupon Id");
			int couponId = Integer.parseInt(readerObj.readLine());
			/*
			 * if (CheckIdStatus(couponId)) { System.out.println("Coupon has expired");
			 * return 0; }
			 */
			Coupon remove = null;
			for (Coupon coup : restaurantObj.couponList) {
				if (coup.getCouponId() == couponId) {
					remove = coup;
					amount = amount - coup.getAmount();
					if (amount <= 0)
						amount = 0;
				}
			}
			if (remove != null) {
				restaurantObj.couponList.remove(remove);
				if (Order.couponList.contains(couponId))
					Order.couponList.remove(couponId);
			}
		}

		return amount;
	}

	/*
	 * protected boolean CheckIdStatus(int couponId) { Date date = new Date();
	 * Coupon couponObj =
	 * Food_App.restaurantsStorage.get(userObj.getRestaurantName()).couponList.get(
	 * couponId); if ((couponObj.getExpiryTime()).after(date))//
	 * formatter.format(date))) return true; return false; }
	 */

	protected int addComboFood(Restaurants restaurantsObj) {
		int amount = 0;
		for (ComboFood iterObj : restaurantsObj.restaurantComboList) {
			System.out.println("Combo Name-" + iterObj.name + "Combo Prize-" + iterObj.getPrize());
			System.out.println("Do you want to add this");
			amount += iterObj.getPrize();
		}
		return amount;
	}

	protected void orderFood() throws IOException {
		Order.setTotalAmount(0);
		List<Order> orderList = new ArrayList<Order>();
		Order userObj;
		int foodId;
		String restaurantName;
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the Restaurant name");
		restaurantName = readerObj.readLine();
		if (Food_App.restaurantName.contains(restaurantName))
			System.out.println("Available coupon are");
		else {
			System.out.println("There is no restaurants available for given name");
			return;
		}
		showCoupon(restaurantName);
		boolean orderContinuation = true;
		Restaurants restaurantObj = restaurantsStorage.get(restaurantName);
		int amount = 0;
		while (orderContinuation) {
			userObj = new Order();
			userObj.setRestaurantName(restaurantName);

			System.out.println("Enter the Food Id");
			foodId = Integer.parseInt(readerObj.readLine());
			if (Food_App.availableFood.containsKey(restaurantName + "_" + foodId)) {
				userObj.setFoodId(foodId);
				Food foodObj = availableFood.get(restaurantName + "_" + foodId);
				userObj.setFoodName(foodObj.getName());
				amount = amount + foodObj.getPrize();
				orderList.add(userObj);
			} else {
				System.out.println("Given Food dooesn't exist");
				System.out.println("do you want to order another food\n1.continue\n0.exit");
				if (Integer.parseInt(readerObj.readLine()) == 0) {
					orderContinuation = false;
				}
			}
		}
		System.out.println("Do you want to add combo food-0/1");
		if (Integer.parseInt(readerObj.readLine()) == 1) {
			amount += addComboFood(restaurantObj);
		}
		amount = setOfferType(restaurantObj, amount);

		Order.setTotalAmount(amount);
		Order.setOrderList(orderList);
	}

	public void showCoupon(String restaurantName) {
		Restaurants obj = restaurantsStorage.get(restaurantName);
		if (obj.couponList.isEmpty()) {
			System.out.println("Given restaurant doen't contains coupon");
			return;
		}
		for (Coupon coupon : obj.couponList) {
			System.out.println("\nCouponId-" + coupon.getCouponId() + "\nCoupon Amount-" + coupon.getAmount()
					+ "\nCouponExpiryTime-" + coupon.getExpiryTime());
		}
	}

	public boolean checkType(String type) {
		if (type.equalsIgnoreCase("sbi") || type.equalsIgnoreCase("hdfc") || type.equalsIgnoreCase("paytm"))
			return true;
		return false;
	}

	public Banking addPaymentOption(CheckOut checkObj) throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		Banking bankingObj;
		System.out.println("Enter the account type");
		String type = readerObj.readLine();
		bankingObj = new Banking(type);

		if (checkType(type)) {
			checkObj.applyDiscount(5);
		}
		return bankingObj;
	}

	/*
	 * public int getCouponId(Order userObj) throws IOException { BufferedReader
	 * readerObj = new BufferedReader(new InputStreamReader(System.in)); String
	 * restaurantsName = userObj.getRestaurantName();
	 * System.out.println("Enter the CouponId"); showCoupon(restaurantsName); return
	 * Integer.parseInt(readerObj.readLine()); }
	 */

	public void showCheckOut() throws IOException {
		Banking bankingObj;
		System.out.println("Enter the Payment");
		CheckOut checkObj = new CheckOut();
		checkObj.setTotalAmount(Order.getTotalAmount());
		bankingObj = addPaymentOption(checkObj);
		checkObj.applyTax();
		checkObj.printOrder();
		Order.setTotalAmount(checkObj.getTotalAmount());
		// checkObj.storeHistory();

	}

	public FoodCart addToCart() throws IOException {
		Print printObj = new Print();
		boolean orderContinuation = true;
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		printObj.displayFood();
		FoodCart cartObj = new FoodCart();
		cartObj.setUserInstance();
		while (orderContinuation) {
			System.out.println("Choose the restaurant-Name");
			String restaurantName = readerObj.readLine();
			System.out.println("Choose the food-Id");
			int foodId = Integer.parseInt(readerObj.readLine());
			cartObj.addToCart(restaurantName, foodId);
			System.out.println("Do you want to continue-0/1");
			int i = Integer.parseInt(readerObj.readLine());
			if (i == 0) {
				orderContinuation = false;
			}
		}
		return cartObj;
	}

	public void addCombo() throws IOException {
		Print printObj = new Print();
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		printObj.displayRestaurants();
		System.out.println("please enter your restaurent name");
		String resName = readerObj.readLine();
		Restaurants restaurantObj = restaurantsStorage.get(resName);
		System.out.println("do you want to add \n1.food \n2.combolist");
		int ch = Integer.parseInt(readerObj.readLine());
		if (ch == 1) {
			addFoodToRestaurants(restaurantObj);
		} else if (ch == 2) {

			List<Integer> comboList = new ArrayList<>();
			printObj.displayFood();
			boolean available = true;
			while (available) {
				System.out.println("Choose the Food id , please enter -1 if you added the food items");
				int foodId = Integer.parseInt(readerObj.readLine());
				if (foodId == -1) {
					available = false;
				} else {
					comboList.add(foodId);
				}
			}
			System.out.println("Enter the combo name");
			String comboName = readerObj.readLine();
			System.out.println("enter the price amount");
			int price = Integer.parseInt(readerObj.readLine());
			ComboFood comboObj = new ComboFood(comboName, comboList, price);
			restaurantObj.addCombo(comboObj);
		}

	}

	protected void buyCartProduct(FoodCart cartObj, Order userObj) throws IOException {
		BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
		int amount = 0;
		// checkOutHistory historyObj=new checkOutHistory();
		System.out.println("Choose the restaurants Name");
		String restaurantName = readerObj.readLine();
		amount = cartObj.getAmount();
		Restaurants restaurantsObj = restaurantsStorage.get(restaurantName);
		// List<Order> orderList = new ArrayList<>();
		setOfferType(restaurantsObj, amount);
	}

	public static void main(String[] args) throws Exception {
		Scanner scannerObj = new Scanner(System.in);
		boolean continuation = true;
		FoodCart cartObj = null;
		while (continuation) {
			Thread.sleep(50);
			System.out.println(
					"\n1.Add an Restraunts\n2.Order the Food\n3.Add to cart\n4.Add food/combo list to restaurants\n5.Display the restaurent details\n6.Buy cart Products\n7.Exist");
			int n = scannerObj.nextInt();
			Food_App homePage = new Food_App();
			Print printObj = new Print();
			switch (n) {
			case 1:
				System.out.println("Please Add your the Restraunts Details");
				homePage.addRestaurentDetails();
				break;
			case 2:
				printObj.displayRestaurants();
				printObj.displayFood();
				homePage.orderFood();
				homePage.showCheckOut();

				break;
			case 3:
				cartObj = homePage.addToCart();
				// cartList.add(cartObj);
				break;
			case 4:
				homePage.addCombo();
				break;
			case 5:
				System.out.println(
						"Display food  \n 1.By veg\n2.Restaurants Discounts\n3.By prize\n4.By Restaurants Name");
				int i = scannerObj.nextInt();
				switch (i) {
				case 1:
					System.out.println("Enter the food type-true/false");
					boolean veg = scannerObj.nextBoolean();
					printObj.printByVeg(veg);
					break;
				case 2:
					System.out.println("Enter the minimum discount");
					int minDiscount = scannerObj.nextInt();
					printObj.printByDiscount(minDiscount);
					break;
				case 3:
					printObj.sortFood();
					break;
				case 4:
					printObj.sortRestaurants();
					break;
				}
				break;
			case 6:
				for (Map.Entry<String, List<Integer>> iterList : cartObj.getCartStorage().entrySet()) {
					System.out.println("\nRestaurant-Name-" + iterList.getKey());
					List<Integer> foodIds = iterList.getValue();
					for (Integer iterFood : foodIds)
						System.out.println("\nFoodName-" + foods.get(iterFood).getName() + "\nFoodPrize-"
								+ foods.get(iterFood).getPrize());
				}
				homePage.buyCartProduct(cartObj, cartObj.getUserInstance());
				break;
			case 7: {
				BufferedReader readerObj = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter the restaurants Name");
				String resturantsName = readerObj.readLine();
				Restaurants restaurantsObj = Food_App.restaurantsStorage.get(resturantsName);
				Performance tempObj = homePage.getRestaurantPerformance();
				restaurantsObj.setRestaurantPerformance(tempObj);
			}
				break;
			case 8:
				System.out.println("Program Terminated");
				continuation = false;
				break;
			}
		}
		scannerObj.close();
	}
}
