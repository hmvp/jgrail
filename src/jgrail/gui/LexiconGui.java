package jgrail.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class LexiconGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917769741613570279L;

	public LexiconGui() {
		super("Lexicon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLayout();
	}
	
	private void createLayout(){
		LayoutManager lm = new GridLayout(1,2);
		JComponent c = new 
		add(lm);
	}
	
}
