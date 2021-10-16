package f21as.coursework.coffeshop.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import f21as.coursework.coffeshop.exceptions.CustomerNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrderFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrderNotFoundException;

public class OrderList {
	
	
	//the customerIDs and orders are stored in a linkedhashmap
	private LinkedHashMap<String, ArrayList<Order>> list;

	/**
	 * 
	 * @param customer
	 * @throws CustomerNotFoundException 
	 */
	//returns an arraylist when a customerID is passed as argument
	public ArrayList<Order> getCustomerOrders(String customer) throws CustomerNotFoundException  {
		
		if(this.list.containsKey(customer))
		return this.list.get(customer);
				
		else throw new CustomerNotFoundException("There are no orders for "+customer);

	}
	
	
	/**
	 * 
	 * @param customer
	 * @throws CustomerNotFoundException 
	 */
	
	//returns the customer order when customerID and orderID are passed as argument
	public Order getCustomerOrder(String customer, String orderID) throws CustomerNotFoundException {
		
		
		if(this.list.containsKey(customer))
		{
			Iterator<Order> orders = this.list.get(customer).iterator();
			while(orders.hasNext())
			{
				Order order = orders.next();
				if(order.getOrderID().equals(orderID))
					return order;
			}
				
			
		}
			
		
		//if you get here meaning that the order specified does not exists		
		throw new CustomerNotFoundException("There are no orders for "+customer);
		
	}
	
	//constructor creates an empty linkedhashmap
	
	public OrderList() throws OrderFileNotFoundException {
		try {
			
			this.list = new LinkedHashMap<String, ArrayList<Order>>();
			
		}
		catch (Exception e) {
			throw new OrderFileNotFoundException("The orders file was not found");
		}
		
		
	}

	//this method adds an order to the list of orders 
	//cutomerID and the order is passed as argument
	public void addOrder(String customer, Order order) 
	{
		//ArrayList to store the orders
		ArrayList<Order> orders;
		//if the orderlist already contains a customer, then the new order is added into the orderList with the same customerID
		if(this.list.containsKey(customer))
		{
			orders = this.list.get(customer);
			orders.add(order);
			
		}
		//if not, then a new entry is made into the ArrayList
		else
		{
			orders = new ArrayList<Order>();
			orders.add(order);
		}
		
		this.list.put(customer, orders);

	}

	/**
	 * 
	 * @param customer
	 * @param order
	 * @throws OrderNotFoundException 
	 */
	// removes an order from the list of existing orders
	//takes customer ID and the order to be removed as argument
	public void removerOrder(String customer, String order) throws OrderNotFoundException {
		
		boolean out = false;
		if(this.list.containsKey(customer))
		{
			ArrayList<Order> orders = this.list.get(customer);
			
			Iterator<Order> iter = orders.iterator();
			while((iter.hasNext()&& !out))
			{
				Order aux = iter.next();
				if(aux.getOrderID().equals(order))
				{
					orders.remove(aux);
					out = true;
				}
					
			}
			
		}
		
		if(!out) 
		{
				//if the order to be removed is not found then this exception is thrown
				throw new OrderNotFoundException(order);		
		}		
	}

	/**
	 * 
	 * @param customer
	 * @throws CustomerNotFoundException 
	 */
	
	//removes the customer from the list of customers after the order is served
	public void removeCustomer(String customer) throws CustomerNotFoundException {
		
		boolean out = false;
		if(this.list.containsKey(customer))
		{
			this.list.remove(customer);
		}
		
		else throw new CustomerNotFoundException(customer); 
		
			
	}
	//gets the LinkedHashMap contaning all the existing orders
	public LinkedHashMap<String, ArrayList<Order>> getList() {
		
		return this.list;
	}
	
	public String toString()
	{
		String output = "";
		Iterator iterC = this.list.keySet().iterator();
		while(iterC.hasNext())
		{
			String customer = (String) iterC.next();
			ArrayList<Order> orders = this.list.get(customer);
			
			Iterator<Order> iterO =  orders.iterator();
			while(iterO.hasNext())
			{
				Order order = iterO.next();
				String st = "Customer: "+customer;
				output=output+"\n"+st+"\n"+order.toString();
			}
		}
		
		return output;
	}
	
	//this method converts the order details to a string array to be stored in the 
	//final generated report
	
	public String toCSV()
	{
		String output = "";
		Iterator iterC = this.list.keySet().iterator();
		String[] aux = {"Timestamp","CustomerID","OrderID","ItemID","Discount"};
		output=this.convertToCSV(aux);
		while(iterC.hasNext())
		{
			String customer = (String) iterC.next();
			ArrayList<Order> orders = this.list.get(customer);
			
			Iterator<Order> iterO =  orders.iterator();
			while(iterO.hasNext())
			{
				Order order = iterO.next();
				String time = Long.toString(order.getCreateDate().getTime());
				String orderID = order.getOrderID();
				String itemID = order.getItemID();
				int discount = order.getDiscount();
				aux= new String[]{time, customer, orderID, itemID, String.valueOf(discount)};
				
				output=output+"\n"+this.convertToCSV(aux);
			}
		}
		
		return output;
	}

//	https://www.baeldung.com/java-csv
	//method to handle special characters while writing into csv file
	private String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
	//Stream is used to join various elements to a single string object
	private  String convertToCSV(String[] data) {
	    return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
	}
	

	//checks if the list of orders is empty or not
	//returns a boolean value
	public boolean isEmpty()
	{
		return this.list.isEmpty();
	}
	
	
	//for stage-2 
	public int getWaiting_Orders_Num() throws CustomerNotFoundException
	{
		int count=0;
		
		Iterator<String> iter = this.list.keySet().iterator();
		while(iter.hasNext())
		{
			String customerID = iter.next();
			ArrayList<Order> orders = this.getCustomerOrders(customerID);
			count = count + orders.size();
		}
		
		return count;
	}
	
	
	
}