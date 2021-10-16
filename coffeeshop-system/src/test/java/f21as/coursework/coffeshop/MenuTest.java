package f21as.coursework.coffeshop;

import static org.junit.Assert.assertArrayEquals;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import f21as.coursework.coffeshop.core.Utils;
import f21as.coursework.coffeshop.data.Category;
import f21as.coursework.coffeshop.data.Menu;
import f21as.coursework.coffeshop.exceptions.CustomerNotFoundException;
import f21as.coursework.coffeshop.exceptions.FrequencyException;
import f21as.coursework.coffeshop.exceptions.InvalidCategoryException;

class MenuTest {
	 private Menu menu;
	 private Utils utils;
	 
	 
	@BeforeEach                                         
	public void setUp() throws Exception {
		
		utils = new Utils();
	    menu = new Menu();
	    menu = utils.instanciateMenu("files/menu.csv");
	    
	}
	


	//Test for getting the price of the item from the menu
	@Test
	public void testGetPriceItem() {
		
		assertEquals(14,menu.getPriceItem("F118"),"hi");
	}

	//test for getting the category of the item from the menu
	@Test
	void testGetCategoryItem() {
		assertEquals(Category.FOOD,menu.getCategoryItem("F118"));
	}

	//test for getting the item name from the menu
	@Test
	void testGetNameItem() {
		assertEquals("Ramen" , menu.getNameItem("F118"));
	} 

	//test for getting the description of the item from the menu
	@Test
	void testGetItemDescription() {
		assertEquals("Spicy ramen",menu.getItemDescription("F118"));
	}

	
	
	//test to check the item descriptions
	@Test
	void testGetItem() {
		String itemStr = "Category: "+"FOOD"+"\n"+"Name: "+"Ramen"+"\n"+"Description: "+"Spicy ramen"+"\n"+"Price: "+"14.0"+"\n";
		assertEquals( itemStr,menu.getItem("F118").toString());
	}

}
