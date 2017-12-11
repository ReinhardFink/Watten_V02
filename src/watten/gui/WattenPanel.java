package watten.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

import javax.swing.*;

import watten.CONSTANTS;
import watten.VirtualPlayer;

public class WattenPanel extends JPanel {

	private static final long serialVersionUID = 3257286928792762417L;
	
	private boolean mitGuatem;
	private String lastCorrectInput;
	private String lastCorrectOutput;
	private VirtualPlayer virtualPlayer;
	
	private JTextArea inputTextArea;
	private JTextArea outputTextArea;
	private JCheckBox mitGuatemCheckBox;
	private PossibilityArrayPanel schlagPanel;
	private PossibilityArrayPanel trumpfPanel;
	
	
	public WattenPanel() {
		this.initComponents();
		this.newGame(true);
		mitGuatemCheckBox.setSelected(mitGuatem);
	}
	
	private void newGame(boolean mitGuatem) {
		this.mitGuatem = mitGuatem;
		lastCorrectInput = "";
		this.lastCorrectOutput = "";
		this.inputTextArea.setText("");
		this.outputTextArea.setText("");
		virtualPlayer = new VirtualPlayer(mitGuatem);
		virtualPlayer.getResult().getTrumpfPossibilityArray().addObserver(trumpfPanel);
		virtualPlayer.getResult().getSchlagPossibilityArray().addObserver(schlagPanel);
		trumpfPanel.resetLabelColors();
		schlagPanel.resetLabelColors();
	}
	
	private void initComponents() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		this.add(createTopLabel(), BorderLayout.NORTH);
		this.add(createCenter(),BorderLayout.CENTER);
	}

	private JComponent createTopLabel() {
		JLabel jHeaderLabel = new JLabel(CONSTANTS.labelProgramHeader);
		jHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jHeaderLabel.setFont(new java.awt.Font("Default", 1, 24));
		return jHeaderLabel;
	}

	private JComponent createCenter() {
		JPanel inputOutputPanel = new JPanel(new GridLayout(2,1));
		inputOutputPanel.add(createInputPanel());
		inputOutputPanel.add(createOutputPanel());
		return inputOutputPanel;
	}

	private JComponent createInputPanel() {
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.add(createControlPanel(),BorderLayout.NORTH);
		inputPanel.add(createSticheInputPanel(),BorderLayout.CENTER);
		inputPanel.add(createResultPanel(),BorderLayout.SOUTH);
		return inputPanel;
	}
	
	private JComponent createControlPanel() {
		JPanel controlPanel = new JPanel(); //new GridLayout(1,2));
		// add to Component (1/1);
		controlPanel.add(new JLabel(CONSTANTS.labelGameWithGuatem));
		// add to Component (1/2)
		mitGuatemCheckBox = new JCheckBox();
		mitGuatemCheckBox.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				if(lastCorrectInput.equals("")) newGame(!mitGuatem);
				else {
					outputTextArea.append(CONSTANTS.ERROR_Game_Status_Unchangeable);
					mitGuatemCheckBox.setSelected(!mitGuatemCheckBox.isSelected());
				}
			}
		});
		controlPanel.add(mitGuatemCheckBox);
		JButton newGame = new JButton(CONSTANTS.labelNewGame);
		newGame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { newGame(mitGuatem); }
		});
		controlPanel.add(newGame);
		return controlPanel;
	}

	private JComponent createSticheInputPanel() {
		inputTextArea = new JTextArea(4,14);
		inputTextArea.setFont(new Font("Monospaced",Font.PLAIN,12));
		inputTextArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					handleNewStich();
			}
		});
		JScrollPane aJScrollPane = new JScrollPane(inputTextArea);
		return aJScrollPane;
	}
	
	private void handleNewStich() {
		StringTokenizer lineCreator = new StringTokenizer(inputTextArea.getText(),"\n");
		if(lineCreator.countTokens() == 0) return;
		while(lineCreator.countTokens() > 1) lineCreator.nextToken();
		if(virtualPlayer.isAddStichPossible(lineCreator.nextToken())) {
			lastCorrectInput = inputTextArea.getText();
			outputTextArea.setText(lastCorrectOutput);
			outputTextArea.append(virtualPlayer.getMessage());
			lastCorrectOutput = outputTextArea.getText();
		} else outputTextArea.append(virtualPlayer.getErrorMessage());
	}
	
	private JComponent createOutputPanel() {
		JPanel outputPanel = new JPanel(new BorderLayout());
		outputTextArea = new JTextArea(10,10);
		JScrollPane aJScrollPane = new JScrollPane(outputTextArea);
		outputPanel.add(aJScrollPane,BorderLayout.CENTER);
		return outputPanel;
	}
	
	private JComponent createResultPanel() {
		JPanel resultPanel = new JPanel(new GridLayout(2,1));
		// add Component to (1/1)
		trumpfPanel = new PossibilityArrayPanel(CONSTANTS.labelColor,CONSTANTS.labelColors);
		resultPanel.add(trumpfPanel.getPanel());
		// add Component to (2/1)
		schlagPanel = new PossibilityArrayPanel(CONSTANTS.labelNumber,CONSTANTS.labelNumbers);
		resultPanel.add(schlagPanel.getPanel());
		return resultPanel;
	}
}