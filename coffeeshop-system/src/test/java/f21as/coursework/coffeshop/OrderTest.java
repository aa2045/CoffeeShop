package f21as.coursework.coffeshop;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import f21as.coursework.coffeshop.data.Order;

public class OrderTest {
	
	// Tesing the constructor with the parameters OrderID and Item ID
	@Test
	void testOrderConstructor1() {
		Order o2 = new Order("oyr9ut000","F116");
		assertEquals("oyr9ut000",o2.getOrderID());
		assertEquals("F116",o2.getItemID());	
	}
	
	//Testing the constructors with the parameters Timestamp/Date , OrderID and ItemID
	@Test
	void testOrderConstructor2and3() {
		Order o1 = new Order("1614406210133","oyr9ut000","F116");
		assertEquals("Sat Feb 27 10:10:10 GST 2021",o1.getCreateDate().toString());
		assertEquals("oyr9ut000",o1.getOrderID());
		assertEquals("F116",o1.getItemID());
	
	}

}
