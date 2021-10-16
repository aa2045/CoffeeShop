package f21as.coursework.coffeshop;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import f21as.coursework.coffeshop.core.CoffeShopEngine;
import f21as.coursework.coffeshop.core.Utils;
import f21as.coursework.coffeshop.data.DiscountList;
import f21as.coursework.coffeshop.exceptions.CustomerNotFoundException;
import f21as.coursework.coffeshop.exceptions.DiscountFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.FailedInitializationException;
import f21as.coursework.coffeshop.exceptions.FrequencyException;
import f21as.coursework.coffeshop.exceptions.MenuFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrderFileNotFoundException;

class CoffeeEngineTest {
	private CoffeShopEngine engine;
	private DiscountList discountlist;
	private Utils utils;
	
	@BeforeEach
	public void setUp() throws FailedInitializationException, MenuFileNotFoundException, DiscountFileNotFoundException, OrderFileNotFoundException, FrequencyException, CustomerNotFoundException {
		//instantiating the CoffeShopEngine class and DiscountList
		utils = new Utils();
		discountlist = new DiscountList();
		engine = CoffeShopEngine.istanciateEngine();
		discountlist = new DiscountList();
		discountlist = Utils.instanciateDiscounts("files/discount.csv");
		
	
	}
	

	
	//Test for the exceptions FailedInitializationException and MenuFileNotFoundException
	@Test
	void testIstanciateEngine() throws OrderFileNotFoundException, DiscountFileNotFoundException, CustomerNotFoundException {
		try {
			CoffeShopEngine.istanciateEngine();
		}
		catch(FailedInitializationException | MenuFileNotFoundException e) {
			assertFalse(e.getMessage().contains(null));
		}
	}

	//Test for item description
	@Test
	void testGetItemDescription() {
		assertEquals("Spicy ramen",engine.getItemDescription("F118"));
	}


	//Test the getCustomerBill(), when the customer has no discount for order
	@Test
	void testGetCustomerBill() throws CustomerNotFoundException {
		String bill = "\ncustomer "+"rz64b"+": \n";
		bill = bill+"Item: "+"I123"+"  "+"Internet1"+"\n";
		bill = bill + "Cost: "+"10.0"+"AED";
		bill = bill + "\n-------------------------------\n";
		bill = bill + "Total AED: "+ "10.0";
		assertEquals(bill, engine.getCustomerBill("rz64b"));
		
	}
	
	//Test for the getCustomerBill(), when the customer has orders where 20% discount rule can be applied
	@Test
	void testGetCustomerBillDiscount() throws CustomerNotFoundException {
		String bill = "\ncustomer "+"yr9ut"+": \n";
		bill = bill+"Item: "+"F116"+"  "+"ClubSandwich"+"\n";
		bill = bill + "Cost: "+"26.0"+"AED";
		bill = bill + "\nafter "+"20% discount: "+"20.8"+"AED";
		bill = bill + "\n-------------------------------\n";
		bill = bill+"Item: "+"F117"+"  "+"ArabitaPasta"+"\n";
		bill = bill + "Cost: "+"23.0"+"AED";
		bill = bill + "\nafter "+"20% discount: "+"18.400000000000002"+"AED";
		bill = bill + "\n-------------------------------\n";
		bill = bill+"Item: "+"F118"+"  "+"Ramen"+"\n";
		bill = bill + "Cost: "+"14.0"+"AED";
		bill = bill + "\n-------------------------------\n";
		bill = bill+"Item: "+"F116"+"  "+"ClubSandwich"+"\n";
		bill = bill + "Cost: "+"26.0"+"AED";
		bill = bill + "\nafter "+"20% discount: "+"20.8"+"AED";
		bill = bill + "\n-------------------------------\n";
		bill = bill+"Item: "+"B112"+"  "+"Abood"+"\n";
		bill = bill + "Cost: "+"7.0"+"AED";
		bill = bill + "\nafter "+"20% discount: "+"5.6000000000000005"+"AED";
		bill = bill + "\n-------------------------------\n";
		bill = bill+"Item: "+"B113"+"  "+"Tea"+"\n";
		bill = bill + "Cost: "+"2.0"+"AED";
		bill = bill + "\nafter "+"20% discount: "+"1.6"+"AED";
		bill = bill + "\n-------------------------------\n";
		bill = bill+"Item: "+"I124"+"  "+"Internet2"+"\n";
		bill = bill + "Cost: "+"17.0"+"AED";
		bill = bill + "\n-------------------------------\n";
		bill = bill + "Total AED: "+ "98.19999999999999";
		assertEquals(bill, engine.getCustomerBill("yr9ut"));
		
	}
	

	
	//Test for checking the discount values
	@Test
	void testgetMyDiscount() {
		
	assertEquals(discountlist.toPrologString().toString(), engine.getMydiscouts().toPrologString().toString());
	}
	
	//Test for the method that calculates the discount
	@Test
	void testCalculateDiscount() {
		ArrayList<String> orders = new ArrayList<>();
		orders.add("oyr9ut000");
		orders.add("oyr9ut001");
		orders.add("oyr9ut003");
		orders.add("oyr9ut004");
		orders.add("oyr9ut005");
		double discount = 0.0;
		discount = engine.calculateDiscount("yr9ut",orders , "discount1(X_00,X_01,X_10,X_11,X_12)");
		System.out.print(discount);
		double expected = 16.799999999999997;
		
		String expectedStr = Double.toString(expected);
		String discountStr = Double.toString(discount);
		
		assertEquals(expectedStr, discountStr);
	}
	


}
