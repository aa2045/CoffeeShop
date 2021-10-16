package f21as.coursework.coffeshop.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

public class CreditInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public CreditInfoFrame() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(12, 12, 12), new Leading(26, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(12, 12, 12), new Leading(45, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(184, 157, 10, 10), new Leading(22, 80, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(12, 12, 12), new Leading(100, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(12, 18, 12, 12), new Leading(80, 12, 12)));
		setSize(351, 132);
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("2021");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {

			BufferedImage myPicture = null;
			try {
				InputStream url = this.getClass().getClassLoader().getResourceAsStream("f21as/coursework/coffeshop/pic/logo.JPG");
				myPicture = ImageIO.read(url);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jLabel3 = new JLabel(new ImageIcon( myPicture ));
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Heriot Watt University");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Coursework");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Group 3");
		}
		return jLabel0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			UIManager.setLookAndFeel(lnfClassname);
		} 
		catch (Exception e) {
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

	public static void ShowCredits() {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CreditInfoFrame frame = new CreditInfoFrame();
				//frame.setDefaultCloseOperation(JFCredits.EXIT_ON_CLOSE);
				frame.setTitle("CoffeShop Coursework - Credits");
				frame.getContentPane().setPreferredSize(frame.getSize());
				BufferedImage myPicture= null;
				 InputStream url = this.getClass().getClassLoader().getResourceAsStream("f21as/coursework/coffeshop/pic/logo.JPG");
					try {
						myPicture = ImageIO.read(url);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon image = new ImageIcon(myPicture);
				
				
				frame.setIconImage(image.getImage());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}

