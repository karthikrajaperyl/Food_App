import java.util.*;

public class Food {

	static int count = 0;
	private int foodId;
	private String foodName;
	private boolean veg;
	private int prize;
	protected Performance qualityObj;
	protected String ingredients;

	protected void setId(int id) {
		this.foodId = id;
	}

	protected int getId() {
		return this.foodId;
	}

	protected void setName(String name) {
		this.foodName = name;
	}

	protected String getName() {
		return this.foodName;
	}

	protected void setPrize(int prize) {
		this.prize = prize;
	}

	protected int getPrize() {
		return prize;
	}

	protected void setVeg(boolean veg) {
		this.veg = veg;
	}

	protected boolean checkVeg() {
		return this.veg;
	}

	public void setFoodQuality(Performance tempObj) {
		this.qualityObj = tempObj;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getIngredients() {
		return this.ingredients;
	}

	@Override
	public String toString() {
		return "\nid = " + this.foodId + "\nname = " + this.foodName + "\nprize = " + this.prize;
	}

}

class ComboFood {
	public String name;
	public List<Integer> comboList;
	private int prize;

	ComboFood(String name, List<Integer> foodIds, int prize) {
		this.name = name;
		this.comboList = foodIds;
		this.prize = prize;
	}

	protected int getPrize() {
		return prize;
	}
}
