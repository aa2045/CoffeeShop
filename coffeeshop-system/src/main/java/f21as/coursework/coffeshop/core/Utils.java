package f21as.coursework.coffeshop.core;

import java.io.DataInputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import f21as.coursework.coffeshop.data.Beverage;
import f21as.coursework.coffeshop.data.Category;
import f21as.coursework.coffeshop.data.Discount;
import f21as.coursework.coffeshop.data.DiscountList;
import f21as.coursework.coffeshop.data.Food;
import f21as.coursework.coffeshop.data.Internet;
import f21as.coursework.coffeshop.data.Item;
import f21as.coursework.coffeshop.data.Menu;
import f21as.coursework.coffeshop.data.Order;
import f21as.coursework.coffeshop.data.OrderList;
import f21as.coursework.coffeshop.exceptions.CustomerNotFoundException;
import f21as.coursework.coffeshop.exceptions.DiscountFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.FrequencyException;
import f21as.coursework.coffeshop.exceptions.MenuFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrderFileNotFoundException;

public class Utils {
	
	// this method reads the orders file and puts the orders with the customer IDs onto the linkedhashmap
	//this method returns OrderList object which is a linkedhashmap
	//Linkedhashmap's value-pair is the Order class object
	public static OrderList instanciateOrders(String fileName) throws FrequencyException, OrderFileNotFoundException
	{
		OrderList list = new OrderList();
		
		String content = FileManager.readFile(fileName);
		
		String[] lines = content.split("\n");
		
		//skip the first line, contains the header of the file
		for(int i =1; i<lines.length; i++)
		{
			Scanner sc = new Scanner(lines[i]);
			
			sc.useDelimiter(","); // split the string using ','
			
			String date = sc.next();
			String customerID = sc.next();
			String orderID = sc.next();
			String itemID = sc.next();
			int discount = Integer.parseInt(sc.next().trim());
			
			Order order = new Order(date, orderID, itemID);
//			order.setDiscount(discount);
			//key is the customerID
			list.addOrder(customerID, order);
			sc.close(); // closes the scanner
			
		}
		
		return list;
	}
	
	//this method calls methods of FileManager class to read menu file and populate the treemap
	// returns a Menu object
	// adds the itemID as a key and Item class object as value
	public static Menu instanciateMenu(String fileName) throws FrequencyException, MenuFileNotFoundException
	{
		Menu list = new Menu();
		
		String content = FileManager.readFile(fileName);
		
		String[] lines = content.split("\n");
		
		//skip the first line, contains the header of the file
		for(int i =1; i<lines.length; i++)
		{
			Scanner sc = new Scanner(lines[i]);
			
			sc.useDelimiter(","); // split the string using ','
			
			Category category = Category.valueOf(sc.next());
			String itemID = sc.next();
			String itemName = sc.next();
			String description = sc.next();
			double cost=Double.parseDouble(sc.next());
			
			Item item = null;
			
			switch (category) 
	        { 
	        case FOOD: 
	            item = Food.instanciateFoodItem(itemName, description, cost); 
	            break; 
	        case BEVERAGE: 
	        	item = Beverage.instanciateBeverageItem(itemName, description, cost); 
	            break; 
	        case OTHER:
	        	item = Internet.instanciateInternetItem(itemName, description, cost);
	        	break;
			
			}
			list.addItem(itemID, item);	
			sc.close(); // closes the scanner
		}
			
		return list;
	}
	
	// Method reads the discount file and stores the data read onto the DiscountList object
	
	public static DiscountList instanciateDiscounts(String fileName) throws FrequencyException, DiscountFileNotFoundException
	{
		DiscountList list = new DiscountList();
		
		String content = FileManager.readFile(fileName);
		
		String[] lines = content.split("\n");
		
		//skip the first line, contains the header of the file
		for(int i =1; i<lines.length; i++)
		{
			Scanner sc = new Scanner(lines[i]);
			
			sc.useDelimiter(","); // split the string using ','
			
			int value = Integer.parseInt(sc.next());
			String rule = sc.next();
			String name = sc.next();
			
			System.out.println(name+" -- "+rule+" -- "+value);
			// assign the Discount object the discount name, value and rule 
			Discount disc = new Discount(name, value, rule);
			list.addDiscount(disc);
			sc.close(); // closes the scanner
			
		}
		
		return list;
	}

	
	
//	public static String[] getItemsPerCategory(String category, Menu menu)
//	{
//		Vector<String> items = new Vector<String>();
//		
//		Iterator iter = menu.getList().keySet().iterator();
//		while(iter.hasNext())
//		{
//			String itemID = (String) iter.next();
//			Category aux = menu.getCategoryItem(itemID);
//			System.out.println(category+" vs. "+aux+"   "+aux.equals(Category.valueOf(category)));
//			if(aux.equals(Category.valueOf(category)))
//				items.add(itemID);
//		}
//		return items.toArray(new String[items.size()]);
//		
//		
//	}

	//gets the items per category, used in MainGUI class to display the menu
	public static String[] getItemsPerCategory(String categoryVal, Menu menu)
	{
		
		Category category = Category.valueOf(categoryVal);
		
		Vector<String> items = menu.getItemsPerCategory(category);
		
		return items.toArray(new String[items.size()]);
		
	}

	
	//static method to generate report 
	public static String generateReport(Menu menu, OrderList list)
	{
		return null;
		
	}
	
// https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
	
	private static String randomGenerator(int n, String AlphaNumericString) 
    { 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
    } 
	
	
	public static String randomCodeGenerator(int n) 
    { 
		if(n<4) n=4;

		String AlphaNumericString1 = "abcdefghijklmnopqrstuvxyz";		
        String AlphaNumericString2 = "04123456789"
                					+ "abcdefghijklmnopqrstuvxyz";
  
        String a= randomGenerator(1, AlphaNumericString1);
        return a+randomGenerator(n-1, AlphaNumericString2);
        
    } 
	
	public static String randomNumberGenerator(int n) 
    { 
  
		// chose a Character random from this String 
        String AlphaNumericString = "0123456789"; 
  
        return randomGenerator(n, AlphaNumericString);
    } 
	
	//this method parses the enum class and gets the categories to be displayed in the GUI class
	public static String[] getCategories(Class<Category> e) {
	    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}
	
	
	
}

