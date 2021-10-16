package f21as.coursework.coffeshop.gui;

import java.awt.Color;


import java.awt.Desktop;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.RandomStringUtils;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;


import f21as.coursework.coffeshop.core.CoffeShopEngine;
import f21as.coursework.coffeshop.core.Utils;
import f21as.coursework.coffeshop.data.Category;
import f21as.coursework.coffeshop.data.Menu;
import f21as.coursework.coffeshop.exceptions.CustomerNotFoundException;

import java.awt.Font;

public class CustomerFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//new customer text area
	private JLabel custLabel;
	private JTextField custField;
	private JButton addButton;
	private JButton billButton;
	
	// for select the category of food
	private JLabel categoryLabel;
	private JComboBox categoryBox;
	
	
	private JTextArea itemDescriptionField;
	private JLabel itemLabel;	
	private JComboBox itemBox;
	private JButton itemButton;
	
	
	private boolean result;
	private String fileName; 
	
	
	private MainGUI main = null;
	
	
	private int Win_textFieldPosX = 50, Unix_textFieldPosX = 60, textFieldWidth = 327, itemHeight = 25;
	
	private int Win_buttonFieldPosX = 387, Unix_buttonFieldPosX = 397, Win_buttonWidth = 85, Unix_buttonWidth = 110;
	
	
	private int Win_authFieldPosX = 200, Unix_authFieldPosX = 215, Win_authFieldWidth= 178, Unix_authFieldWidth = 172;
	
	
	
	private CoffeShopEngine engine;
	
	
	
	public CustomerFrame(CoffeShopEngine engine) {
		
		this.engine = engine;
		initComponents();	
	
	}

	
	public void setMainGUI(MainGUI main)
	{
		this.main = main;
	}
	
	
		
	private void initComponents() {
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(null, "Customer Orders", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null, null),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		setLayout(new GroupLayout());
//		int textFieldPosX = 50, textFieldWidth = 327, itemHeight = 25;
		int selectFieldWidth = 80;
		
		
		String value = OSValidator.getOS();
	
		int textFieldPosX = 0;
		int buttonFieldPosX=0;
		int buttonWidth=0;
		int listFieldPosX=0;
		int listFieldWidth=0;
		
		
		if(value.equals("win"))
		{
			textFieldPosX = Win_textFieldPosX;
			buttonFieldPosX = Win_buttonFieldPosX;
			buttonWidth = Win_buttonWidth;
			listFieldPosX = Win_authFieldPosX;
			listFieldWidth = Win_authFieldWidth;
		}
		else
		{
			textFieldPosX = Unix_textFieldPosX;
			buttonFieldPosX = Unix_buttonFieldPosX;
			buttonWidth = Unix_buttonWidth;
			listFieldPosX = Unix_authFieldPosX;
			listFieldWidth = Unix_authFieldWidth;
		}
		
		add(getCustomerLabel(), new Constraints(new Leading(3, 67, 12, 12), new Leading(0, itemHeight, 12, 12)));
		add(getCustomerField(), new Constraints(new Leading(textFieldPosX, textFieldWidth, 12, 12), new Leading(0, itemHeight, 12, 12)));
		add(getAddButton(), new Constraints(new Leading(buttonFieldPosX, buttonWidth, 12, 12), new Leading(0, itemHeight, 12, 12)));
		add(getCategoryLabel(), new Constraints(new Leading(3, 67, 12, 12), new Leading(35, itemHeight, 12, 12)));		
		add(getCategoryBox(), new Constraints(new Leading(textFieldPosX, textFieldWidth, 12, 12), new Leading(35, itemHeight, 12, 12)));
		add(getBillButton(), new Constraints(new Leading(buttonFieldPosX, buttonWidth, 12, 12), new Leading(35, itemHeight, 12, 12)));
		
		
		
		add(getItemLabel(), new Constraints(new Leading(3, 67, 12, 12), new Leading(70, itemHeight, 12, 12)));		
		add(getItemBox(), new Constraints(new Leading(textFieldPosX, selectFieldWidth, 12, 12), new Leading(70, itemHeight, 12, 12)));

		JScrollPane scroll = new JScrollPane (getItemField());
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
//		add(scroll, new Constraints(new Leading(listFieldPosX, listFieldWidth, 12, 12), new Leading(70, 70, 12, 12)));
		add(scroll, new Constraints(new Leading(listFieldPosX-45, listFieldWidth+45, 12, 12), new Leading(70, 70, 12, 12)));
		add(getItemButton(), new Constraints(new Leading(buttonFieldPosX, buttonWidth, 12, 12), new Leading(70, itemHeight, 12, 12)));
		
		setSize(337, 90);
		
		//enable/disable corresponding buttons
		updateButtonState();
		addButton.setEnabled(true);
		
	}
	

	
	public JButton getItemButton() {
		if (itemButton == null) {
			itemButton = new JButton();
			itemButton.setText("Add");
		
			
			/*when the button is clicked*/
			itemButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
//		        	 final WaitFrame frame = new WaitFrame();
//		        	 frame.addWindowListener(new java.awt.event.WindowAdapter(){});		 
		        	 
		        	 String itemID = (String) itemBox.getSelectedItem();
		        	 String clientID = (String) custField.getText();
		        	 System.out.println("Item selected: "+itemID+"   client:  "+clientID);
		        	 engine.addNewOrder(clientID, itemID);
		        	 System.out.println(engine.getordertList().toString());
		        	 state3();
		         }
			});
						
		}
		itemButton.setEnabled(false);
		return itemButton;
	}
	
	
	
	
	public JButton getBillButton() {
		if (billButton == null) {
			billButton = new JButton();
			billButton.setText("BILL");
		
			
			/*when the button is clicked*/
			billButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
//		        	 final WaitFrame frame = new WaitFrame();
//		        	 frame.addWindowListener(new java.awt.event.WindowAdapter(){});		 
		        	 
		        	 String clientID = (String) custField.getText();
		        	 String bill;
					bill = engine.getCustomerBill(clientID);
					itemDescriptionField.setText(bill);
					 state1();
		         }
			});
						
		}
		billButton.setEnabled(false);
		return billButton;
	}
	
	
	private JLabel getCustomerLabel() {
		if (custLabel == null) {
			custLabel = new JLabel();
			custLabel.setText("Client:");
		}
		return custLabel;
	}
	
	private JTextField getCustomerField() {
		if (custField == null) {
			custField = new JTextField();
			custField.setEditable(false);
		}
		return custField;
	}
	
	
	private JTextArea getItemField() {
		if (itemDescriptionField == null) {
			itemDescriptionField = new JTextArea();
			
			itemDescriptionField.setFont(new Font("Times New Roman", Font.BOLD, 12));
			itemDescriptionField.setEditable(false);
			itemDescriptionField.setText("");

		}
		return itemDescriptionField;
	}
	
	public void setCutomerField(String param){		
		custField.setText(param);
	}
	
	private JLabel getCategoryLabel() {		
		if (categoryLabel == null) {
			categoryLabel = new JLabel();
			categoryLabel.setText("TYPE:");
		}
		return categoryLabel;
	}
	
	
	public JComboBox getCategoryBox() {
		if (categoryBox == null) {
			categoryBox = new JComboBox();
			
			String[] categories = Utils.getCategories(Category.class);
			categoryBox.setModel(new DefaultComboBoxModel<String>(categories));
			
			categoryBox.addActionListener(new ActionListener() {

		            public void actionPerformed(ActionEvent e)
		            {
		            	System.out.println("Selected Category: "+categoryBox.getSelectedItem().toString());
		            	refreshItems(categoryBox.getSelectedItem().toString(), engine.getMenu());
		            }
		        });
				
			}
			
			
		return categoryBox;
	}
	
	
	
	
	private JLabel getItemLabel() {		
		if (itemLabel == null) {
			itemLabel = new JLabel();
			itemLabel.setText("Item");						
		}
		return itemLabel;
	}
	

	private void refreshItems(String category, Menu menu)
	{
		
		String[] items = Utils.getItemsPerCategory(category, menu);
		
		itemBox.setModel(new DefaultComboBoxModel<String>(items));
		
	}
	
	
	private JComboBox getItemBox() {
			
		ArrayList<String> entities = null;
		if (itemBox == null) {
			itemBox = new JComboBox();
			
			this.refreshItems(Category.FOOD.toString(), engine.getMenu());
			
			itemBox.addActionListener(new ActionListener() {

	            public void actionPerformed(ActionEvent e)
	            {
	                //Execute when a selection has been made
	            	System.out.println("Selected: "+itemBox.getSelectedItem().toString());
	            	itemDescriptionField.setText(engine.getMenu().getItemDescription(itemBox.getSelectedItem().toString()));
	            	
	            }
	        });
			
//			itemBox.setModel(new DefaultComboBoxModel(entities.toArray()));
		}
		return itemBox;
	}
	
	

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText("NEW");

			addButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		                 
		        	 String customerId = Utils.randomCodeGenerator(5);
		        	 custField.setText(customerId);
		        	 state2();
		         }
		      });		     
		}
		
		return addButton;
	}
	
	
	private void state1()
	{
	 itemButton.setEnabled(false);
   	 billButton.setEnabled(false);
   	 addButton.setEnabled(true);
   	 custField.setText("");
   	 
   	
	}
	
	private void state2()
	{
	 itemButton.setEnabled(true);
   	 billButton.setEnabled(false);
   	 addButton.setEnabled(false);
	}
	

	private void state3()
	{
	 itemButton.setEnabled(true);
   	 billButton.setEnabled(true);
   	 addButton.setEnabled(false);
	}
	
	public void updateButtonState(){
		
	}
}
