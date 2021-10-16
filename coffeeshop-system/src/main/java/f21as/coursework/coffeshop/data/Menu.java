package f21as.coursework.coffeshop.data;



import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;


import f21as.coursework.coffeshop.core.Utils;
import f21as.coursework.coffeshop.exceptions.InvalidCategoryException;
import f21as.coursework.coffeshop.exceptions.MenuFileNotFoundException;

//Menu class deals with creation of a treemap to store the menu items.
//contains methods to get items per category
//
public class Menu {
	
	
	private TreeMap<String, Item> list;
	
	// returns the treemap containing itemID and Item object as key-value pair
	public TreeMap getList() {
		
		return list;

	}

	/**
	 * 
	 * @param item
	 */
	// returns the unit price when an itemcode is passed as argument
	public double getPriceItem(String code) {
	
		Item item = this.list.get(code);
		
		return item.getCost();

	}
	// returns the category when an itemID is passed
	public Category getCategoryItem(String code) {
		
		Item item = this.list.get(code);
		
		return item.getCategory();

	}
	// returns the item name when the itemID is passed, calls the Item class method getName()

	public String getNameItem(String code)
	{
		Item item = this.list.get(code);
		
		return item.getName();
		
	}
	
	//This method returns a vector of all the items for a given category
	public Vector<String> getItemsPerCategory(Category category)
	throws InvalidCategoryException{
		
		Vector<String> output = new Vector();
		//iterating through the itemID which is the key
		Iterator<String> iter = this.list.keySet().iterator();
		//while theres a next itemID
		while(iter.hasNext())
		{
			String itemID = iter.next();
			//value pair of the itemID is stored in Item object-item
			Item item = this.list.get(itemID);
			//get the category of the passed itemID and store it in Category type-aux
			Category aux = this.getCategoryItem(itemID);
			//if the category of the itemID matches with the category passed in the argument of the method
			//then add the itemID into the vector
			//this makes sure that itemIDs are separated according to categories
			if(aux.equals(category))
				output.add(itemID);
		}
		return output;
			
		
		
	}
	
	// returns the item description for the itemcode passed
	
	public String getItemDescription(String code) {
		
		Item item = this.list.get(code);
		
		return item.getDescription();

	}
	
	//Constructor 
	//Constructor creates an empty treemap and throws MenuFileNotFoundException when Menu file is not found.
	public Menu() throws MenuFileNotFoundException {
		  // create an empty menu
        try{
           
            this.list = new TreeMap<String, Item>();
        }
       
        catch(Exception e){
            throw new MenuFileNotFoundException("Menu file is not found");
        }
       
	}

	/**
	 * 
	 * @param code
	 * @param item
	 */
	//adds the string ItemID and Item object to the treemap as key-value pair
	public void addItem(String code, Item item) {
		
		this.list.put(code, item);
		
	}

	/**
	 * 
	 * @param code
	 */
	//returns an Item class object
	// the value pair of the treemap is returned when the itemID is passed as argument
	public Item getItem(String code) {
		
		return this.list.get(code);
		
	}

	//overriding the toString() 
	public String toString()
	{
		String output = "";
		Iterator iterI = this.list.keySet().iterator();
		while(iterI.hasNext())
		{
			String id = (String) iterI.next();
			Item item = this.list.get(id);
			
			String st = "ItemID: "+id;
			output=output+"\n"+st+"\n"+item.toString();
			
		}
		
		return output;
	}
	
	// method used in CoffeShopEngine class
	
	public String toPrologString()
	{
		String output = "";
		//iterating through the itemIDs
		Iterator<String> iterI = this.list.keySet().iterator();
		//while there is a next itemID
		while(iterI.hasNext())
		{
			String id = iterI.next();
			//getting the value pair for the passed itemID and storing it in an Item object item
			Item item = this.list.get(id);
			// storing the Item object in a prolog format, easier to calculate rule-based discounts
			output=output+"\n"+item.toPrologString();
			
		}
		
		// returns a string of the name of the item
		output=output+"\n\n";
		
		// for each category in Category enum
		for (Category category : Category.values()) 
		{ 
		    String categoryRule=category.toString().toLowerCase()+"(X) :- ";
		    //contains all the itemIDs of the category passed as argument
			Vector<String> items = this.getItemsPerCategory(category);
			//iterating through those itemIDs 
			iterI = items.iterator();
			//while there a next itemID
			while (iterI.hasNext())
			{
				//get the name of the item when itemID is passed in lowercase
				String name = this.getNameItem(iterI.next()).toLowerCase();
				//append the name of the item to the category rule
				categoryRule=categoryRule+name+"(X)";
				if(iterI.hasNext())
					categoryRule=categoryRule+"; ";
				else
					categoryRule=categoryRule+".";
			}
			//append the categoryRule to the output
			output=output+categoryRule+"\n";
			
		}
		
		
		return output;
	}
	
	
	
}