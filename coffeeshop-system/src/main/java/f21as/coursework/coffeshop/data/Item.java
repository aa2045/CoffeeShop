package f21as.coursework.coffeshop.data;


public abstract class Item {

	private String name;
	private String description;
	private double cost;
	private int counter;
	private Category category;
	
	private String prologRule;

	
	/**
	 * 
	 * @param name
	 * @param cat
	 * @param desc
	 * @param cost
	 */
	

	public Item(Category category, String name, String description, double cost ){
		this.category = category;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.counter=0;
		
	}

	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Double getCost() {
		return this.cost;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public int getCounter() {
		return this.counter;
	}

	public void updateCounter() {

		counter++;
	}

	public abstract int getExpectedTime();
	
	public String toString()
	{
		String category = "Category: "+this.category;
		String name = "Name: "+this.name;
		String description = "Description: "+this.description;
		String cost = "Price: "+this.cost;
		
		return category+"\n"+name+"\n"+description+"\n"+cost+"\n";
		
		
	}

	//method called in Menu class
	//returns a String 
	public String toPrologString()
	{	//name of the item is converted to lower case
		String name=this.name.toLowerCase();
		return name+"(X) :- order(X,"+name+").";
		
	}

}