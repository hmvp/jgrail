package test;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jgrail.JGrail;
import jgrail.gui.ParserGUI;

public class TestGui {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try {
		        UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		    } 
		    catch (UnsupportedLookAndFeelException e) {
		       // handle exception
		    }
		    catch (ClassNotFoundException e) {
		       // handle exception
		    }
		    catch (InstantiationException e) {
		       // handle exception
		    }
		    catch (IllegalAccessException e) {
		       // handle exception
		    }

		
		
		JGrail x = new JGrail();
		//x.setVisible(true);
		ParserGUI x1 = new ParserGUI(x);
	}

}
