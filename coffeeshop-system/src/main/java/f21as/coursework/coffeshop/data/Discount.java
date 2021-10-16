package f21as.coursework.coffeshop.data;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Discount {

	private HashMap<String, Integer> deal;
	private int discount;
	private String name;


	/**
	 * 
	 * @param discount
	 */
	public Discount(String name, int discount) {
		
		this.name = name.trim();
		this.discount = discount;
		this.deal = new HashMap<String, Integer>();
	}

	
	public Discount(String name, int discount, String rules) 
	{
		this(name, discount);
		
		String[] rulesArr = rules.split("\\+");
		
		for(int i=0; i<rulesArr.length; i++)
		{
			String[] rule = rulesArr[i].split(":");
			this.deal.put(rule[1], Integer.parseInt(rule[0]));
			
		}
		
		
	}
	
	
	
	
	
	/**
	 * 
	 * @param item
	 * @param count
	 */
	public void addItem(String item, int count) {
		
		this.deal.put(item, count);
		
	}


	public HashMap<String, Integer> getDeal() {
		return this.deal;
	}

	public int getDiScount() {
		
		return this.discount;
	}
	
	
	
	private String renderRule(String item, int count, String var)
	{
		String out = "";
		Vector<String> aux = new Vector();
		if(count==1)
		{
			out=item+"("+var+")";
			return out;
		}
		
		for(int i=0; i<count; i++)
		{
				if(i>0) 
					out=out+", ";
				String v = var+""+i;
				aux.add(v);
				out=out+item+"("+v+")";
		}
				
		String a = "";
		for(int i=0; i<aux.size(); i++)
		{
			
			if(i+1==aux.size())
			{}
			else
			{
				for(int j=i+1; j<aux.size(); j++)
				{
					a=a+", "+var+""+i+"\\="+var+""+j;
				}
					
			}
		}
		
		return out+a;		
		
	}
	
	
	private String renderRule(String item, Vector<String> vars)
	{
		String out = "";
		int count=0;
		
		Iterator<String> iter = vars.iterator();
		
		while(iter.hasNext())
		{
			if(count>0)
			{
				out=out+", ";
			}
			
			out=out+item+"("+iter.next()+")";
			count++;
		}
		
			
		String a = "";
		for(int i=0; i<vars.size(); i++)
		{
			
			if(i+1==vars.size())
			{}
			else
			{
				for(int j=i+1; j<vars.size(); j++)
				{
					a=a+", "+vars.elementAt(i)+"\\="+vars.elementAt(j);
				}		
			}
		}
		
		return out+a;		
		
	}
	
	
	public String toPrologString()
	{
		System.out.println("---------------------------");
		Iterator<String> iter = this.deal.keySet().iterator();
		String rule = "";
		int a = 0;
		Vector<String> vars = new Vector();
		while(iter.hasNext())
		{
			String item = iter.next();
			int count = this.deal.get(item);
			System.out.println("Item: "+item+"   count: "+count); 
			Vector<String> vars_aux = new Vector();
			for(int j=0; j<count; j++)
			{
				vars.add("X_"+a+j);
				vars_aux.add("X_"+a+j);
			}
				
			
			if(a>0) rule=rule+", ";
//			rule=rule+this.renderRule(item.toLowerCase(), count, "X_"+a);
			rule=rule+this.renderRule(item.toLowerCase(), vars_aux);
			a++;
		}
		
		String head = name+"(";
		iter = vars.iterator();
		while(iter.hasNext())
		{
			head=head+iter.next();
			if(iter.hasNext())
				head=head+",";
			else head=head+")";
		}
		
		return head+" :- "+rule+".";
	}

	
}