package watten.gui;

import javax.swing.*;
import java.awt.*;

public class FindRechterApplet extends JApplet {

	private static final long serialVersionUID = 3546365024081097528L;

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
				public void run() { createAndShowGUI(); }
		});
	};
	
	private static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame aFrame = new JFrame("Find Schlag & Trumpf");
		aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aFrame.setContentPane(new WattenPanel());
		aFrame.setSize(new Dimension(500,500));
		aFrame.setLocation(130,30);
		aFrame.setVisible(true);
	}

	public void init() {
		setContentPane(new WattenPanel());
	}
}

