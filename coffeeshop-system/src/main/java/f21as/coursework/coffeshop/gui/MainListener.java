package f21as.coursework.coffeshop.gui;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class MainListener {

	static public void enableMenu (JMenuItem jComponent){
		ActionListener listener = null;
		if ( jComponent.getName() == "exit") {
			listener =new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			};
		}
		else if (jComponent.getName() == "help"){
			listener =new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					HelpInfoFrame.ShowHelp();
				}
			};
		}
		else if (jComponent.getName() == "credits"){
			listener =new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CreditInfoFrame.ShowCredits();
				}
			};
		}

		jComponent.addActionListener(listener);
	}
}