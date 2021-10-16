package f21as.coursework.coffeshop.data;


public class Beverage extends Item {



	private boolean coffe;

	public int getExpectedTime() {
		// TODO - implement Beverage.getExpectedTime
		throw new UnsupportedOperationException();
	}

	
	//inherited class calls the constructor of the parent Item class
	
	protected Beverage(String name, String des, double cost)
	{
		super(Category.BEVERAGE, name, des, cost);
	}
	
	
	/**
	 * 
	 * @param name
	 * @param des
	 * @param cost
	 */
	
	// method to instantiate beverage class and returns beverage objects
	public static Beverage instanciateBeverageItem(String name,  String des, double cost) {
		// TODO - implement Beverage.instanciateBeverageItem
		
		Beverage beverage = new Beverage(name, des, cost);
		return beverage;
				
	}

}