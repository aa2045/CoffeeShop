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

public class HelpInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	public HelpInfoFrame() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(58, 10, 10), new Leading(30, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(58, 12, 12), new Leading(62, 12, 12)));
		setSize(494, 156);
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Trial Application v1.0");
		}
		return jLabel0;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Coffeshop Support System");
		}
		return jLabel1;
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

	/**
	 * Main entry of the class.
	 * Note: This class is only created so that you can easily preview the result at runtime.
	 * It is not expected to be managed by the designer.
	 * You can modify it as you like.
	 */
	public static void ShowHelp() {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HelpInfoFrame frame = new HelpInfoFrame();
				frame.setTitle("CoffeShop Service - Official Help");
				frame.getContentPane().setPreferredSize(frame.getSize());
				BufferedImage myPicture= null;
				 InputStream url = this.getClass().getClassLoader().getResourceAsStream("f21as/coursework/coffeshop/pic/logo.JPG");
					try {
						myPicture = ImageIO.read(url);
					} 
					catch (IOException e1) {
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
