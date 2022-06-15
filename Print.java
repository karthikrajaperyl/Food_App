import java.io.*;
import java.util.*;
import java.util.Map.Entry;

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

	protected void sortFood() {
		List<Entry<Integer, Food>> tempList = new ArrayList<>(Food_App.foods.entrySet());

		Collections.sort(tempList, (l1, l2) -> l1.getValue().getPrize().compareTo(l2.getValue().getPrize()));
		for (Map.Entry<Integer, Food> entry : tempList) {
			Food tempObj = entry.getValue();
			System.out.println(tempObj.toString());
		}
	}

	protected void sortRestaurants() {
		Collections.sort(Food_App.restaurantsList, new SortByName());
		for (Restaurants iterList : Food_App.restaurantsList) {
			System.out.println(iterList.toString());
		}
	}
}

class SortByName implements Comparator<Restaurants> {
	@Override
	public int compare(Restaurants obj1, Restaurants obj2) {
		return obj1.resturantName.compareTo(obj2.resturantName);
	}
}

class SortByRating implements Comparator<Performance> {
	@Override
	public int compare(Performance obj1, Performance obj2) {
		return obj1.rating.compareTo(obj2.rating);
	}
}
