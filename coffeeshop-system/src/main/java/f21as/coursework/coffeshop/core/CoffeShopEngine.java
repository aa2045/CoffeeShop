package f21as.coursework.coffeshop.core;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.Scanner;

import f21as.coursework.coffeshop.data.Category;
import f21as.coursework.coffeshop.data.Discount;
import f21as.coursework.coffeshop.data.DiscountList;
import f21as.coursework.coffeshop.data.Item;
import f21as.coursework.coffeshop.data.Menu;
import f21as.coursework.coffeshop.data.Order;
import f21as.coursework.coffeshop.data.OrderList;
import f21as.coursework.coffeshop.exceptions.CustomerNotFoundException;
import f21as.coursework.coffeshop.exceptions.DiscountFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.FailedInitializationException;
import f21as.coursework.coffeshop.exceptions.FrequencyException;
import f21as.coursework.coffeshop.exceptions.MenuFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrderFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrderNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrdersToFileException;

public class CoffeShopEngine {
	// private data members
	private OrderList myordertList;
	private Menu myMenu;
	private DiscountList mydiscouts;
	private String report="";
	private String discountPolicy = MAX_DISCOUNT;
	private double totalIncome=0;

	
	private static String ORDERS_FILE="files/orderfile.csv";
	private static String MENU_FILE="files/menu.csv";
	private static String DISCOUNTS_FILE="files/discount.csv";
	private static String MAX_DISCOUNT="Max_discount";
	private static String MIN_DISCOUNT="Min_discount";
	
	//private Constructor
	private CoffeShopEngine(OrderList myorders, Menu mymenu, DiscountList mydiscount) throws CustomerNotFoundException
	{
		this.mydiscouts = mydiscount;
		this.myMenu = mymenu;
		this.myordertList = myorders;
		initializeNumbers();
	}
	
	//returns a linkedhashmap of OrderList object containing the orders
	public OrderList getordertList() {
		
		return this.myordertList;
	}

	// returns a Menu object containing the details of the menu stored in treemap
	public Menu getMenu() {
		
		return this.myMenu;
	}
	
	// returns DiscountList object containing all details of the discounts
	public DiscountList getMydiscouts() 
	{
		return this.mydiscouts;
	}

	

	/**
	 * 
	 * @param customer
	 */
//	public void removeCustomer(String customer) {
//
//	}
	
	//method to remove a order when customerID and OrderID is passed
	
	public void removeOrder(String customer, String order) throws OrderNotFoundException
	{
		this.myordertList.removerOrder(customer, order);
		System.out.print("Order removed");
	}
	
	/**
	 * 
	 * @param order
	 * @param menu
	 * 
	 */
	//method updates the quantity of the item for the given customerId and orderId
	public void updateItemCounter(String customer, String orderID) {
		
		Order order;
		try 
		{
			order = this.myordertList.getCustomerOrder(customer, orderID);
			
			String itemID = order.getItemID();
			
			updateItemCounter(itemID);
			
			
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	//Calls the updateCounter() of Item class to update the frequency of the items ordered
	 public void updateItemCounter(String itemID) {
		
		Item item = this.myMenu.getItem(itemID);
		item.updateCounter();
		
		
		
	}
	
// when initializing the engine, it reads all the existing orders from the file. The number of items and discounts recalculated need to be updated
	private void initializeNumbers() throws CustomerNotFoundException
	{
		LinkedHashMap<String, ArrayList<Order>> orders = this.myordertList.getList();
		Iterator<String> iter = orders.keySet().iterator();
		while(iter.hasNext())
		{
			String customerID = iter.next();
			try 
			{
				Iterator<Order> ordersL = this.myordertList.getCustomerOrders(customerID).iterator();
				while(ordersL.hasNext())
				{
					Order order = ordersL.next();
					Item item = this.myMenu.getItem(order.getItemID());
					item.updateCounter();
				}
				
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getCustomerBill(customerID);
		}
	}
	 //static method returns an object for CoffeShopEngine class; this method called in GUI
	public static CoffeShopEngine istanciateEngine() throws FailedInitializationException, MenuFileNotFoundException, OrderFileNotFoundException, DiscountFileNotFoundException, CustomerNotFoundException {
		
		OrderList orders = null;
		Menu menu = null;
		DiscountList discounts = null;
		
		try 
		{	//assigining the OrderList object to orders
			orders = Utils.instanciateOrders(ORDERS_FILE);
			//assiging the Menu object to menu
			menu = Utils.instanciateMenu(MENU_FILE);
			//assiging the DiscountList object to discounts
			discounts = Utils.instanciateDiscounts(DISCOUNTS_FILE);
			
		} catch (FrequencyException e) {
			// TODO Auto-generated catch block
			//need to catch this exception and handle it
			e.printStackTrace();
			throw new FailedInitializationException(e.toString());
		}
		
		CoffeShopEngine engine = new CoffeShopEngine(orders, menu, discounts);
		return engine;
		
				
	}

	/**
	 * 
	 * @param customer
	 * @param item
	 */
	//adds the passed item and customerID as an order for that customer
	public void addNewOrder(String customer, String item) 
	{
		
		int count;
		try 
		{	//size of the arraylist is assigned to count
			count = this.myordertList.getCustomerOrders(customer).size();
		} 
		//catch the exception when customer is not found
		catch (CustomerNotFoundException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//this is not an error, it happens when the customer is ordering the first item
			count=0;
		}
		//creates an OrderID for the customer
		String num = String.format("%03d", count);
		String orderID= "o"+customer+num;
		//adding the new order to the order object
		Order order = new Order(orderID, item);
		System.out.println("Adding order "+orderID+" for customer "+customer);
		//appending the the order to the linkedhashmap 
		this.myordertList.addOrder(customer, order);
		//item counter is updated
		this.updateItemCounter(item);
	}

	/**
	 * 
	 * @param code
	 */
	//get the item description of the passed item code
	public String getItemDescription(String code) 
	{	
		Item item = this.myMenu.getItem(code);
		return item.getDescription();
	}

	//method adds the new orders placed to the orderfile.csv file
	
	public void saveOrders() throws OrdersToFileException
	{	//converts the OrderList object to a single string 
		String content = this.myordertList.toCSV();
		
		try {
			FileManager.overWriteFile(ORDERS_FILE, content);
		} catch (FrequencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//exception is thrown if it cant be written to file
			throw new OrdersToFileException(e);
		}
	
	}
	
	//returns a string with name of the item and the number of items sold.
	private String getSoldItems(Category category)
	{
		String output ="\n**************** "+ category +" *******************\n";
		Iterator<String> items = this.myMenu.getItemsPerCategory(category).iterator();
		while(items.hasNext())
		{
			String itemID = items.next();
			Item item = this.myMenu.getItem(itemID);
			if(item.getCounter()>0)
				output=output+item.getName()+"  ("+itemID+")  : "+item.getCounter()+"\n";
		}
		
		
		return output;
		
	}
	
	//method to parse the menu to string, this is called in generateReport()  
	private String menuToString()
	{
		String menu="";
		try {
			Scanner scanner = new Scanner(new File("files/menu.csv"));
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
				menu+=scanner.nextLine() + "\n";
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return menu;
	}
		
		
	//generates the final report that contains details of the menu, customers orders and the counter of the items ordered
	public String generateReport()
	{
		String output="";
		String customerbill = "";
		for (Category category : Category.values()) { 
		     output=output+this.getSoldItems(category);
		}
		//iterates through the key of the linkedhashmap myorderList
		Iterator<String> customersIter = this.myordertList.getList().keySet().iterator();
		//menuread contains a string of all the menu items
		String menuread = menuToString();
		String stars = "***********************************"+"\n";
		String mennuu = "\t\tMENU" + "\n";
		String stars1 = "**********************************" + "\n";
		String starss = "**********************************"+"\n";
		String ord = "\t\tCustomer Orders" + "\n";
		String starss2 = "*********************************" + "\n";
				
		this.report = this.report + stars+ mennuu + stars1 + menuread + "\n\n" + starss + ord + starss2;
		//while there is a next customerID
		while(customersIter.hasNext())
		{
			String customerID = customersIter.next();
			try {
				//appending the Bill for the customerID to the report
				this.report=this.report+customerBilltoString(customerID, true);
				
				//catch the exception and print the error message when the customer is not found
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		String ss = "**********************************"+"\n";
		String freq = "\t\tItem frequency" + "\n";
		String ss2 = "**********************************" + "\n";
		//report is returned as a string
		return this.report+"\n\n"+ss+freq+ss2 +output;
	}
	
	//takes customerID as a argument
	// and returns a string containing menu, discount, orderlist, orderID and itemname
	public String toPrologString(String customerID)
	{
		String output="";
		//appends the itemname and category rules to output
		output=output+this.myMenu.toPrologString()+"\n\n";
		//
		output=output+this.mydiscouts.toPrologString();
				
		OrderList ordersL = this.myordertList;
		
		ArrayList<Order> orders;
		try {
			// the arraylist of a customerID's order is stored in order
			orders = ordersL.getCustomerOrders(customerID);
			//exception is caught when the customer is not found
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//create an empty arraylist of type Order
			orders = new ArrayList<Order>();
		}
		
		String aux="";
		//iterating through the orders arraylist
		Iterator<Order> iterO = orders.iterator();
		while(iterO.hasNext())
		{	
			Order order = iterO.next();
			//find the item of the order
			Item item = this.myMenu.getItem(order.getItemID());
			//appending the orderID and the item name 
			aux=aux+"order("+order.getOrderID()+", "+item.getName().toLowerCase()+").\n";
		}
		
		//appending the string orderID and itemname to the output string
		output=aux+"\n\n"+output;
		return output;
	}
	
	// returns a string of the bill for the order used
	//method called in getcustomerBill()
	private String customerBilltoString(String customer, boolean updateBill) throws CustomerNotFoundException
	{	
//		double bill=0;
//		String output="\ncustomer "+customer+": \n";
//		ArrayList<Order> orders = this.myordertList.getCustomerOrders(customer);
//		Iterator<Order> iter = orders.iterator();
//		while(iter.hasNext())
//		{
//			Order order = iter.next();
//			Item item = this.myMenu.getItem(order.getItemID());
//			output=output+"Item: "+order.getItemID()+"  "+item.getName()+"\n";
//			output=output+"Cost: "+item.getCost()+" AED";
//			if(order.getDiscount()>0)
//			{
//				double discounted = item.getCost() * ((100 - order.getDiscount())*0.01);
//				
//				output=output+"\n"+order.getDiscount()+"% discount: "+discounted+" AED";
//				bill=bill+discounted;
//			}
//			else {
//				bill=bill+item.getCost();
//			}
//			output=output+"\n---------------------------------\n";	
//			
//		}
//		output=output+"Total AED: "+bill;
//		return output;
		
		double bill=0;
		String billString="\ncustomer "+customer+": \n";
		//arraylist of orders for the given customerID
		ArrayList<Order> orders = this.myordertList.getCustomerOrders(customer);
		//iterating through the orders
		Iterator<Order> i = orders.iterator();
		while(i.hasNext())
		{	
			Order order = i.next();
			// get the item for that order and assigning it to Item object
			Item item = this.myMenu.getItem(order.getItemID());
			//appending itemID, itemname that was ordered 
			billString=billString+"Item: "+order.getItemID()+"  "+item.getName()+"\n";
			//appending cost of the item to bill string
			billString=billString+"Cost: "+item.getCost()+"AED";
			//if discount is applicable
			if(order.getDiscount()>0)
			{	//calculate the discounted price for the item
				double discounted = item.getCost() * ((100 - order.getDiscount())*0.01); 
				//append it to the bill
				billString=billString+"\nafter "+order.getDiscount()+"% discount: "+discounted+"AED";
				// true then add the discount to the totalIncome and add it to the bill for the customer
				if(updateBill) this.totalIncome=totalIncome+discounted;// totalIncome is for report
				bill=bill+discounted;
			}
			else {
				// true then add the itemcost to the totalIncome and add it to the bill for the customer
				if(updateBill) this.totalIncome=totalIncome+item.getCost();
				bill=bill+item.getCost();
			}
			billString=billString+"\n-------------------------------\n";	
			
		}
		billString=billString+"Total AED: "+bill;
		
		///////////
		if(updateBill) billString=billString+"\n"+"Partial Income: "+this.totalIncome+"\n\n\n";
		
		//returning the billstring that contains the bill for the customer
		return billString;	
		
	}
	
	/**
	 * 
	 * @param customer
	 * @throws InterruptedException 
	 */
	//returns the final customer bill when a customer iD is passed
	
	public String getCustomerBill(String customer) 
	{
		
		ArrayList<String> goals = new ArrayList<String>();
		//calls the private method toPrologString() and assigns the string of menu, o
		String prologProgram = this.toPrologString(customer);
//		System.out.println("#############################################");
//		System.out.println(prologProgram);
//		System.out.println("#############################################");
		//iterates through the discount vector
		Iterator<Discount> iter = this.mydiscouts.getList().iterator();
		while(iter.hasNext())
		{	
			Discount discount = iter.next();
			//discount vector is converted to a string
			String toProlog = discount.toPrologString();
			// the toProlog string is chunked to only get the discount val and added to the goal arraylist
			String goal = toProlog.split(":-")[0].trim();
			goals.add(goal);
			
		}
		
		DiscountCalculator calculator=null;
		try 
		{
			calculator = DiscountCalculator.instanciateDiscountCalculator(prologProgram, goals);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// in case there is a problem in calculating the discount, no discount is applied!!! the bill calculation continues.
			e.printStackTrace();
			//FrequencyException is thrown if an error is encounterd
		} catch (FrequencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(calculator!=null)
		{
			HashMap<String, ArrayList<ArrayList<String>>> maps = calculator.getMatchingMatrix();
			
			Iterator<String> iterG = maps.keySet().iterator();
			String finalDiscountGoal=null;
			ArrayList<String> finaldiscountedOrders=null;
			double finaldiscountedBill=0;
			
			while(iterG.hasNext())
			{
				String goal = iterG.next();
				ArrayList<ArrayList<String>> solutions = maps.get(goal);
				
				Iterator<ArrayList<String>> iterS = solutions.iterator();
				while(iterS.hasNext())
				{
					ArrayList<String> aux = iterS.next();
					System.out.println("printing aux");
					System.out.println(aux);
					double discount = calculateDiscount(customer, aux, goal);
					if(this.discountPolicy.equals(MAX_DISCOUNT))
					{
						if(discount>finaldiscountedBill)
						{
							finaldiscountedBill=discount;
							finaldiscountedOrders=aux;
							finalDiscountGoal=goal;
						}
							
					}
					else
						if(this.discountPolicy.equals(MIN_DISCOUNT))
						{
							if (finaldiscountedBill==0)
								finaldiscountedBill=(discount+0.001);
							if(discount<finaldiscountedBill)
							{
								finaldiscountedBill=discount;
								finaldiscountedOrders=aux;
								finalDiscountGoal=goal;
							}
								
						}
					
				}
				
				System.out.println("The final discounted orders are: "+finaldiscountedOrders);
				System.out.println("Saving: "+finaldiscountedBill);
				
				if(finaldiscountedOrders!=null)
				{
					Iterator<String> orders = finaldiscountedOrders.iterator();
					while(orders.hasNext())
					{
						String orderID = orders.next();
						try 
						{
							Order order = this.myordertList.getCustomerOrder(customer, orderID);
							int discount = this.getDiscountValue(finalDiscountGoal);
							order.setDiscount(discount);
							
						} catch (CustomerNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}

				
			}
		}
		
//		try 
//		{
//						
//			double bill=0;
//			String billString="\ncustomer "+customer+": \n";
//			ArrayList<Order> orders = this.myordertList.getCustomerOrders(customer);
//			Iterator<Order> i = orders.iterator();
//			while(i.hasNext())
//			{
//				Order order = i.next();
//				Item item = this.myMenu.getItem(order.getItemID());
//				billString=billString+"Item: "+order.getItemID()+"  "+item.getName()+"\n";
//				billString=billString+"Cost: "+item.getCost()+"AED";
//				if(order.getDiscount()>0)
//				{
//					double discounted = item.getCost() * ((100 - order.getDiscount())*0.01); 
//					billString=billString+"\nafter "+order.getDiscount()+"% discount: "+discounted+"AED";
//					this.totalIncome=totalIncome+discounted;
//					bill=bill+discounted;
//				}
//				else {
//					this.totalIncome=totalIncome+item.getCost();
//					bill=bill+item.getCost();
//				}
//				billString=billString+"\n-------------------------------\n";	
//				
//			}
//			billString=billString+"Total AED: "+bill;
//			
//			///////////
//			this.report=this.report+billString+"\n"+"Partial Income: "+this.totalIncome+"\n\n\n";
////			return this.customerBilltoString(customer);
//			
//		} catch (CustomerNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//		
		
		try {
			return customerBilltoString(customer, false);
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	
	
	
	private int getDiscountValue(String discountRule)
	{
		Iterator<Discount> iter = this.mydiscouts.getList().iterator();
		Discount discount=null;
		while(iter.hasNext())
		{
			Discount d = iter.next();
			String toProlog = d.toPrologString();
			String goal = toProlog.split(":-")[0].trim();
			if(goal.equals(discountRule))
			{
				discount = d;
				break;
			}
			
		}
		int value=0;
		if(discount!=null)
			//contains the discount to apply to the selected orders
			value = discount.getDiScount();
		return value;
	}

	
	// calculates the discount for a customerID when all his orders are passed as an arraylist with the discountrules
	//check if the discount can be applied for that customer
	public double calculateDiscount(String customerID, ArrayList<String> orders, String discountRule)
	{
		System.out.print(orders);
//		Iterator<Discount> iter = this.mydiscouts.getList().iterator();
//		Discount discount=null;
//		while(iter.hasNext())
//		{
//			Discount d = iter.next();
//			String toProlog = d.toPrologString();
//			String goal = toProlog.split(":-")[0].trim();
//			if(goal.equals(discountRule))
//			{
//				discount = d;
//				break;
//			}
//			
//		}
//		int value=0;
//		if(discount!=null)
//			//contains the discount to apply to the selected orders
//			value = discount.getDiScount();
		
		int value=this.getDiscountValue(discountRule);
		double total=0;
		Iterator<String> oIter = orders.iterator();
		while (oIter.hasNext())
		{
			String orderID = oIter.next();
			try 
			{
				Order order = this.myordertList.getCustomerOrder(customerID, orderID);
				double price = this.myMenu.getPriceItem(order.getItemID());
				total = total + price;
				
			} catch (CustomerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// total contain the total cost for the selected items before discount
		System.out.println("-----------------------------------------------");
		System.out.println(orders);
		System.out.println(discountRule);
		System.out.println("Total before discount: "+total);
		double discounted = total * ((100 - value)*0.01);
		System.out.println("Total after discount: "+discounted);
		System.out.println("-----------------------------------------------");
		double savings = total - discounted;
		System.out.println("Saving value: "+savings);
		return savings;
	}
	
	
}