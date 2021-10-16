package f21as.coursework.coffeshop.gui;



import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import f21as.coursework.coffeshop.core.CoffeShopEngine;
import f21as.coursework.coffeshop.core.Constants;
import f21as.coursework.coffeshop.core.FileManager;
import f21as.coursework.coffeshop.core.RefreshAnnotationThread;
import f21as.coursework.coffeshop.core.Utils;
import f21as.coursework.coffeshop.exceptions.CustomerNotFoundException;
import f21as.coursework.coffeshop.exceptions.DiscountFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.FailedInitializationException;
import f21as.coursework.coffeshop.exceptions.FailedReportException;
import f21as.coursework.coffeshop.exceptions.FrequencyException;
import f21as.coursework.coffeshop.exceptions.MenuFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrderFileNotFoundException;
import f21as.coursework.coffeshop.exceptions.OrdersToFileException;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;	
	
	/*menu items*/
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuHelp;
	private JMenuItem menuItemExit;
	private JMenuItem menuItemHelp;
	private JMenuItem menuItemCredit;	
		
//	private JLabel HWLogo;
	private CustomerFrame customerFrame;
	private ReportFrame reportFrame;
	private OrdersFrame ordersFrame;
	
		
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	
//	private static int WIN_Width = 493;
//	private static int UNIX_Width = 530;
	
	private static int WIN_Width = 530;
	private static int UNIX_Width = 530;

	
	private CoffeShopEngine engine;
	
	private HashMap entities = new HashMap();
	
	
	public CoffeShopEngine getEngine()
	{
		return this.engine;
	}
	
	
	public static String localDir()
	{
		File localdir = null;
		try 
		{
			localdir = new File(MainGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			localdir = localdir.getParentFile();
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("LocalDirectory: "+localdir.getAbsolutePath());
		return localdir.getAbsolutePath();
	}
	
	public MainGUI() throws FailedInitializationException, MenuFileNotFoundException, OrderFileNotFoundException, DiscountFileNotFoundException, CustomerNotFoundException  {
		
			
			
		    this.engine = CoffeShopEngine.istanciateEngine();
	
			addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent e)
	        {
	          dispose();
	          System.exit(0); //calling the method is a must
	        }
	     });
		 
		initComponents();
	}
	
	
	private void initComponents() {
		setLayout(new GroupLayout());
		
		OrdersFrame ordFrame = getOrdersFrame(engine);
		ReportFrame repFrame = getReportFrame(engine);

		String value = OSValidator.getOS();
		int width = 0;
		if(value.equals("win"))
			width = 600;
		else
			width = MainGUI.UNIX_Width;	
		
		add(getCustomerFrame(engine), new Constraints(new Leading(12, width, 12, 12), new Leading(15, 170, 12, 12)));
		
		add(repFrame, new Constraints(new Leading(12, width, 12, 12), new Leading(200, 95, 12, 12)));
		add(ordFrame, new Constraints(new Leading(12, width, 12, 12), new Leading(300, 200, 12, 12)));
	
	
		
		
		setJMenuBar(getJMenuBar0());


		setSize(560, 600);

		this.setResizable(false);
//		this.setResizable(true);

	}

	protected void exit(int i) {
		System.exit(i);
	}

	/**************************************************************************************
	 * create menu items
	 **************************************************************************************/
	private JMenuBar getJMenuBar0() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMenuFile());
			menuBar.add(getMenuHelp());
		}
		return menuBar;
	}

	private JMenu getMenuFile() {
		if (menuFile == null) {
			menuFile = new JMenu();
			menuFile.setText("File");
			menuFile.setOpaque(false);
			menuFile.add(getMenuItemExit());
		}
		return menuFile;
	}
	
	private JMenuItem getMenuItemExit() {
		if (menuItemExit == null) {
			menuItemExit = new JMenuItem();
			menuItemExit.setText("Exit");
			menuItemExit.setName("exit");
			menuItemExit.setOpaque(false);
			MainListener.enableMenu(menuItemExit);
		
			menuItemExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						engine.saveOrders();
						try {
							saveReport("finalReport.txt");
						} catch (FailedReportException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (OrdersToFileException e1) {
						// TODO Auto-generated catch block
						//needs to be handled
						e1.printStackTrace();
					}
					dispose();
				}
			});
		}
		return menuItemExit;
	}
	
	private JMenu getMenuHelp() {
		if (menuHelp == null) {
			menuHelp = new JMenu();
			menuHelp.setText("?");
			menuHelp.setOpaque(false);
			menuHelp.add(getMenuItemHelp());
			menuHelp.add(getMenuItemCredit());
		}
		return menuHelp;
	}

	private JMenuItem getMenuItemHelp() {
		if (menuItemHelp == null) {
			menuItemHelp = new JMenuItem();
			menuItemHelp.setText("Help");
			menuItemHelp.setName("help");
			menuItemHelp.setOpaque(false);
			MainListener.enableMenu(menuItemHelp);
		}
		return menuItemHelp;
	}
	
	private JMenuItem getMenuItemCredit() {
		if (menuItemCredit == null) {
			menuItemCredit = new JMenuItem();
			menuItemCredit.setText("Credits");
			menuItemCredit.setName("credits");
			menuItemCredit.setOpaque(false);
			MainListener.enableMenu(menuItemCredit);
		}
		return menuItemCredit;
	}
	
	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			UIManager.setLookAndFeel(lnfClassname);
		} 
		catch (Exception e){
			System.err.println("The class " + PREFERRED_LOOK_AND_FEEL
					+ " cannot be installed on this platform:" + e.getMessage());
			String lnfClassname = UIManager.getSystemLookAndFeelClassName();
//			String lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			System.err.println("Installing default 'LOOK_AND_FEEL' class: "+lnfClassname);
			try {
				System.err.println("Trying...");
				UIManager.setLookAndFeel(lnfClassname);
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	
	private CustomerFrame getCustomerFrame(CoffeShopEngine engine) {
		if (customerFrame == null) {
			customerFrame = new CustomerFrame(engine);
			
		}
		return customerFrame;
	}
	
	private ReportFrame getReportFrame(CoffeShopEngine engine) {
		if (reportFrame == null) {
			reportFrame = new ReportFrame(engine);
			
		}
		return reportFrame;
	}
	
	private OrdersFrame getOrdersFrame(CoffeShopEngine engine) {
		if (ordersFrame == null) {
			ordersFrame = new OrdersFrame(engine);
		}
		return ordersFrame;
	}
	
	
	private void saveReport(String fileName) throws FailedReportException
	{
		try 
		{
			FileManager.overWriteFile(fileName, engine.generateReport());
		} catch (FrequencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FailedReportException("Failed to save the report "+fileName);
		}
	}
	
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainGUI frame;
				try 
				{
					frame = new MainGUI();
					
//					frame.setDefaultCloseOperation(MainGUI.EXIT_ON_CLOSE);
					frame.setDefaultCloseOperation(MainGUI.HIDE_ON_CLOSE);
					
					frame.setTitle("Heriot Watt CofeeShop Service - Trial Application");
					
					BufferedImage myPicture= null;
					InputStream url = this.getClass().getClassLoader().getResourceAsStream("f21as/coursework/coffeshop/pic/logo.JPG");
					try {
						myPicture = ImageIO.read(url);
					} 
					catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon image = new ImageIcon(myPicture);				
					
					frame.setIconImage(image.getImage());				
					frame.getContentPane().setPreferredSize(frame.getSize());

					frame.pack();
								
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					SwingUtilities.updateComponentTreeUI(frame);
											 
					Thread thread = new Thread(new RefreshAnnotationThread(frame.ordersFrame));
					thread.start();
			
				} catch (FailedInitializationException | MenuFileNotFoundException | OrderFileNotFoundException | DiscountFileNotFoundException | CustomerNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				 

			}
		});
	}
	

}
