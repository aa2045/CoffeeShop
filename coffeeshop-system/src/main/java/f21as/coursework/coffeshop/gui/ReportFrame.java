package f21as.coursework.coffeshop.gui;

import java.awt.event.ActionEvent;




import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;


import f21as.coursework.coffeshop.core.CoffeShopEngine;
import f21as.coursework.coffeshop.core.FileManager;
import f21as.coursework.coffeshop.exceptions.FrequencyException;

public class ReportFrame extends JPanel{

	private static final long serialVersionUID = -3471402394495794418L;

	/* label */
	private JLabel inputFile;
	private JLabel typeLabel;

	/* text field */
	private static JTextField folderField;

	/* combo box */
	@SuppressWarnings("rawtypes")
	private static JComboBox keyBox;

	private JButton browseButton;
	private static JButton savingButton;
	
	
	private int Win_textFieldPosX = 50, Unix_textFieldPosX = 60, textFieldWidth = 327, itemHeight = 25;
	
	private int Win_buttonFieldPosX = 387, Unix_buttonFieldPosX = 397, Win_buttonWidth = 85, Unix_buttonWidth = 110;
	
	
	private CoffeShopEngine engine;
	
	public ReportFrame(CoffeShopEngine engine) {
		this.engine = engine;
		initComponents();
		
	}
	

	
	private void initComponents() {
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(null, "Saving Panel", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null, null),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		setLayout(new GroupLayout());
		
		
		String value = OSValidator.getOS();
		int textFieldPosX = 0;
		int buttonFieldPosX=0;
		int buttonWidth=0;
//		int authFieldPosX=0;
//		int authFieldWidth=0;
		
		
		if(value.equals("win"))
		{
			textFieldPosX = Win_textFieldPosX;
			buttonFieldPosX = Win_buttonFieldPosX;
			buttonWidth = Win_buttonWidth;
			
		}
		else
		{
			textFieldPosX = Unix_textFieldPosX;
			buttonFieldPosX = Unix_buttonFieldPosX;
			buttonWidth = Unix_buttonWidth;
			
		}
		
		int textFieldWidth = 327, itemHeight = 25;
		int selectFieldWidth = 80;
		
		add(getInputFile(), new Constraints(new Leading(3, 67, 12, 12), new Leading(0, itemHeight, 12, 12)));
		add(getFolderField(), new Constraints(new Leading(textFieldPosX, textFieldWidth, 12, 12), new Leading(0, itemHeight, 12, 12)));
		add(getBrowseButton(), new Constraints(new Leading(buttonFieldPosX, buttonWidth, 12, 12), new Leading(0, itemHeight, 12, 12)));
		
		add(getTypeLabel(), new Constraints(new Leading(3, 67, 12, 12), new Leading(35, itemHeight, 12, 12)));		
		add(getKeyBox(), new Constraints(new Leading(textFieldPosX, selectFieldWidth, 12, 12), new Leading(35, itemHeight, 12, 12)));
		add(getSavingButton(), new Constraints(new Leading(buttonFieldPosX, buttonWidth, 12, 12), new Leading(35, itemHeight, 12, 12)));
		
		
		setSize(337, 91);
		
		//enable/disable GUI items
		updateItemState();
	}
	
	private JLabel getInputFile() {
		if (inputFile == null) {
			inputFile = new JLabel();
			inputFile.setText("PDF Path");
		}
		return inputFile;
	}

	private JTextField getFolderField(){
		if (folderField == null) {
			folderField = new JTextField();
			folderField.setText("");
		}
		return folderField;
	}
	
	public static void setFolderField(String param) {
		folderField.setText(param);
	}

	private JLabel getTypeLabel() {
		if (typeLabel == null) {
			typeLabel = new JLabel();
			typeLabel.setText("Type");
		}
		return typeLabel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox getKeyBox() {
		
		System.out.println("Creating report...");
		if (keyBox == null) {
			keyBox = new JComboBox();
			if(true)
			{
				List<String> keys = new ArrayList<String>();
				keys.add("TXT");
				keys.add("PDF");
				keyBox.setModel(new DefaultComboBoxModel(keys.toArray()));
			}
			
		}		
		
		
		return keyBox;
	}

	public JButton getSavingButton() {
		if (savingButton == null) {
			savingButton = new JButton();
			savingButton.setText("Save");
			
			savingButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 
		        	 final WaitFrame frame = new WaitFrame();
					 frame.addWindowListener(new java.awt.event.WindowAdapter(){});
		        	 
					 try 
					 {
						FileManager.overWriteFile(folderField.getText()+".txt", engine.generateReport());
					} catch (FrequencyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 
					 @SuppressWarnings("rawtypes")
					 SwingWorker worker = new SwingWorker() {
						 @Override
						 protected Object doInBackground() throws Exception {
		        	 
							 		        	 
							 
//							 return 0;
							return 0;
						 }
						 
						 @Override
						 protected void done() {
							 frame.updateStatus("");
							 JOptionPane.showMessageDialog(null, "The Report has been created succesfully ", "", JOptionPane.INFORMATION_MESSAGE);
							 frame.dispose();
						 }
					 };
					 
					 worker.execute();
					 frame.setVisible(true);
		         }
			});
		}
		return savingButton;
	}
	
	
	


	
	private JButton getBrowseButton() {
		if (browseButton == null) {
			browseButton = new JButton();
			browseButton.setText("Browse");

			final JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			browseButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		            int returnVal = fileDialog.showOpenDialog(getRootPane());
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		               java.io.File file = fileDialog.getSelectedFile();
			              
		               folderField.setText(file.getAbsolutePath());
		              
		            }
		            else{
		               folderField.setText("");           
		            }      
		         }
		      });		     
		}
		return browseButton;
	}
	
	public void updateItemState(){
		savingButton.setEnabled(true);
		browseButton.setEnabled(true);
		
	}
}
