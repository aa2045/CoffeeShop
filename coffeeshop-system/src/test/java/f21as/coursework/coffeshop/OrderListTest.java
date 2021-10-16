package f21as.coursework.coffeshop;

import static org.junit.Assert.assertArrayEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import com.sun.tools.javac.util.Assert;

import f21as.coursework.coffeshop.core.Utils;
import f21as.coursework.coffeshop.data.Menu;
import f21as.coursework.coffeshop.data.Order;
import f21as.coursework.coffeshop.data.OrderList;
import f21as.coursework.coffeshop.exceptions.CustomerNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrderNotFoundException;

class OrderListTest {

	private OrderList orderlist;
	private Utils utils;
	 
	@BeforeEach                                         
	public void setUp() throws Exception {
		
		utils = new Utils();
	    orderlist = new OrderList();
	    orderlist = utils.instanciateOrders("files/orderfile.csv");
	    System.out.print(orderlist.getCustomerOrders("rz64b"));
	    
	}
	
	//tests failing need to check
//	@Test
//	void testGetCustomerOrders() {
//		assertEquals(,orderlist.getCustomerOrders("yr9ut"),"hi");
//	}

	@Test
	void testGetCustomerOrder() throws CustomerNotFoundException {
		ArrayList<Order> orderArrayList = new ArrayList<Order>();
		Order o = new Order("1614424497692", "orz64b001", "I123");
		orderArrayList.add(o);
		assertEquals(orderArrayList.toString(), orderlist.getCustomerOrders("rz64b").toString());
	}

	//the test below checks if the exception is thrown when invalid customer id is passed
	@Test
	void invalidCustomer()  {
		
		try {
		 orderlist.getCustomerOrder("4b", "orz64b001" );
		 fail("Invalid customerID - should throw exception");

		} catch (CustomerNotFoundException e) {
			
		assertTrue(e.getMessage().contains("4b"));
		}
		
	}

	//the test below checks if the exception is thrown when invalid customer id and orderID is passed
	@Test
	void invalidOrder()  {
		
		try {
		 orderlist.removerOrder("4b", "001" );
		 fail("Invalid customerID and OrderID - should throw exception");

		} catch (OrderNotFoundException e) {
			
		assertTrue(e.getMessage().contains("001"));
		}
		
	}
//Orderlist is not empty
	@Test
	void testIsEmpty() {
		assertEquals(false, orderlist.isEmpty());
	}


}
