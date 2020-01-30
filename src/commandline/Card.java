package commandline;

public class Card {
	private String name;
	private int[] categories;
	
	
	public Card(String name, int[] categories) {
		this.name = name;
		this.categories = categories;

	}
	
	public int[] getCategories() {
		return categories;
	}

	public String getName() {
		return name;
	}
	
	public void print() {
		System.out.println(name + ":" + categories[0]+ categories[1]+ categories[2]+ categories[3]+ categories[4]);
	}

}
