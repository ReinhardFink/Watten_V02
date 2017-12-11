package watten.gui;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import watten.logic.Possibility;

public class PossibilityArrayPanel implements Observer {
	
	private JPanel aPanel;
	private Color[] colorArray = { Color.RED, Color.BLACK, Color.GREEN };
	private String titel;
	private String[] labelArray;
	
	public PossibilityArrayPanel(String titel, String[] labelArray) {
		this.titel = titel;
		this.labelArray = labelArray;
		initComponents();
	}

	// updated by PossibilityArray
	public void update(Observable observable, Object arg) {
		Possibility[] values = (Possibility[])arg;
		int minimumLength = Math.min(values.length,labelArray.length);
		for(int labels = 0; labels < minimumLength; labels++) {
			aPanel.getComponent(labels + 1).setForeground(colorArray[values[labels].possibility + 1]);
			aPanel.repaint();
		}
	}
	
	public JComponent getPanel() { return aPanel; }
	
	public void resetLabelColors() {
		for(int labels = 0; labels < labelArray.length;labels++) {
			aPanel.getComponent(labels + 1).setForeground(Color.BLACK);
			aPanel.repaint();
		}
	}
	
	private void initComponents() {
		aPanel = new JPanel();
		aPanel.add(new JLabel(titel));
		for(int i = 0; i < labelArray.length;i++) {
			aPanel.add(new JLabel(labelArray[i]));
		}
	}
	
	
}
