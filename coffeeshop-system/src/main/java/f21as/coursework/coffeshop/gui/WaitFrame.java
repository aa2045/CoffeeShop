package f21as.coursework.coffeshop.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

public class WaitFrame extends JFrame {

	private static final long serialVersionUID = -6713609752324883969L;

	private JProgressBar jProgressBar0;
	private JPanel jPanel0;
	private JLabel statusLabel;
	private JLabel outputLabel;
	private Semaphore semaphore;
	
	private String message;
	
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
	public WaitFrame() {
		initComponents();
		
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


		setIconImage(image.getImage());
	}

	public void updateStatus(String status){
		statusLabel.setText(status);
	}

	public void setSemaphore (Semaphore sem) {
		this.semaphore = sem;
	}
	
	public void unlockSem(){
		semaphore.release();
	}
	
	public void updateMessage(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public void updateOutput(String output){

		String htmlTag = "<html>";
		output = output.trim();		
		
		if (output.compareTo("")!=0 && output.compareTo("\\n")!=0){
			if (outputLabel.getText().compareTo("")==0){
				outputLabel.setText(htmlTag + output + htmlTag);
			}
			else{
				String temp = outputLabel.getText();
				temp = temp.substring(0, temp.length() - htmlTag.length());
				outputLabel.setText(temp + "<br>" + output+ htmlTag);
			}
		}
	}
	
	private void initComponents() {
		setTitle("CoffeShop Service - Trial Application");
		setLayout(new GroupLayout());
		add(getStatusLabel(), new Constraints(new Leading(16, 313, 12, 12), new Leading(8, 20, 10, 10)));
		add(getJPanel0(), new Constraints(new Leading(9, 390, 10, 10), new Bilateral(70, 12, 56)));
		add(getJProgressBar0(), new Constraints(new Leading(16, 383, 12, 12), new Leading(42, 12, 12)));
		setSize(411, 284);

	}

	private JLabel getOutputLabel() {
		if (outputLabel == null) {
			outputLabel = new JLabel();
			outputLabel.setText("");

		}
		return outputLabel;
	}

	private JLabel getStatusLabel() {

		if (statusLabel == null) {
			statusLabel = new JLabel();
		
		}
		return statusLabel;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createTitledBorder(null, "Status :", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getOutputLabel(), new Constraints(new Leading(6, 10, 10), new Leading(10, 10, 10)));
		}
		return jPanel0;
	}

	private JProgressBar getJProgressBar0() {
		if (jProgressBar0 == null) {
			jProgressBar0 = new JProgressBar();
			jProgressBar0.setIndeterminate(true);
		}
		return jProgressBar0;
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
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WaitFrame frame = new WaitFrame();

				frame.setDefaultCloseOperation(WaitFrame.EXIT_ON_CLOSE);

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
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}
