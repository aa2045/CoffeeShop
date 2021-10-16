package f21as.coursework.coffeshop.data;


public class Internet extends Item {

	public int getExpectedTime() {
		// TODO - implement Internet.getExpectedTime
		throw new UnsupportedOperationException();
	}

	
	protected Internet(String name,  String des, double cost)
	{
		super(Category.OTHER, name, des, cost);
	}
	
	/**
	 * 
	 * @param name
	 * @param des
	 * @param cost
	 */
	// method to instantiate Internet class and returns Internet objects
	public static Internet instanciateInternetItem(String name,  String des, double cost) {
		// TODO - implement Internet.instanciateInternet
		Internet internet = new Internet(name, des, cost);
		return internet;
	}

}