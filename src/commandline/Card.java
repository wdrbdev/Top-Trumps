package commandline;

import java.util.ArrayList;

public class Card {
	private String name;
	ArrayList<Integer> theCardAttributeNumericalValues;
	
	ArrayList<String> categories; // For Albert

	public Card(String name, ArrayList<Integer> theCardAttributeNumericalValues, ArrayList<String> categories) {
		this.name = name;
		this.categories = categories;
		this.theCardAttributeNumericalValues =  theCardAttributeNumericalValues;
		
	}

	public ArrayList<Integer> getValues() {
		return theCardAttributeNumericalValues;
	}

	public String getName() {
		return name;
	}
	
	
}
