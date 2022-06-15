import java.io.*;
import java.util.*;

public class Print {

	protected void displayRestaurants() {
		System.out.println("\nAvailable Restaurants are");
		for (String resName : Food_App.restaurantsStorage.keySet()) {
			System.out.println("\n" + resName);
		}
	}

	protected void displayFood() {
		System.out.println("\nAvailable foods are");
		for (Map.Entry<String, Food> mapIter : Food_App.availableFood.entrySet()) {
			String[] food = mapIter.getKey().split("_");
			Food foodObj = mapIter.getValue();
			System.out
					.println("\nfoodId-" + food[1] + "\nFoodName-" + foodObj.getName() + "\nRestaurentName-" + food[0]);
		}

		/*
		 * System.out.println("\nAvailable foods are"); for (ComboFood combo :
		 * restaurantComboList) { String[] food = mapIter.getKey().split("_"); Food
		 * foodObj = mapIter.getValue(); System.out .println("\nfoodId-"
		 * +combo.getPrize() + "\nFoodName-" + foodObj.getName() + "\nRestaurentName-" +
		 * food[0]); }
		 */
	}

	protected void displayFoodwithGivenRestaurant(List<Food> foods) {
		for (Food food : foods) {
			System.out.println(
					"\nfoodId-" + food.getId() + "  FoodName-" + food.getName() + "  amount " + food.getPrize());
		}
	}

	protected void getOrderHistoryFromFile() {
		try {
			File file = new File("history.txt");
			System.out.println("your order details are");
			BufferedReader readerObj = new BufferedReader(new FileReader(file));
			String st;
			while ((st = readerObj.readLine()) != null) {
				System.out.println(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	protected void printOrderHistoryToFile(Order obj) {
		try {
			FileWriter fileObject = new FileWriter("history.txt");
			String info = obj.toString();
			fileObject.write(info);
			fileObject.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	protected void printByVeg(boolean veg) {
		Restaurants.restaurantFoodList.stream().filter(tempObj -> tempObj.checkVeg() == veg)
				.forEach(tempObj -> System.out.println("Available foods are" + tempObj.getName()));
	}

	protected void printByDiscount(int discount) {
		Food_App.restaurantsList.stream().filter(tempObj -> tempObj.offerType.percentage > discount)
				.forEach(tempObj -> System.out.println("Available foods are" + tempObj.resturantName));
	}
}
