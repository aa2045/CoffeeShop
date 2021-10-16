package f21as.coursework.coffeshop.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import f21as.coursework.coffeshop.exceptions.DiscountFileNotFoundException;

public class DiscountList {

	private Vector<Discount> list;

	public DiscountList() throws DiscountFileNotFoundException{
		try {
		this.list = new Vector<Discount>();
	}
		catch(Exception e)
		{
			throw new DiscountFileNotFoundException("The discount file was not found");
		}
	}

	/**
	 * 
	 * @param discount
	 */
	public void addDiscount(Discount discount) {
		this.list.add(discount);
	}

	public Vector<Discount> getList() {
		return this.list;
	}

	
	public String toPrologString()
	{
		String output = "";
		Iterator<Discount> iter = this.list.iterator();
		while(iter.hasNext())
		{
			Discount rule = iter.next(); 
			output=output+"\n"+rule.toPrologString();
			
			
		}
		
		return output;
	}
	
}