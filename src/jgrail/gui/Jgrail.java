package jgrail.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import buoy.widget.BFrame;
import buoy.widget.BLabel;
import buoy.widget.BList;
import buoy.widget.BMenu;
import buoy.widget.BMenuBar;
import buoy.widget.BMenuItem;
import buoy.widget.BScrollPane;
import buoy.widget.BSplitPane;
import buoy.widget.BTabbedPane;
import buoy.widget.BorderContainer;
import buoy.xml.WidgetDecoder;

public class Jgrail {
	private BFrame window;
	private BorderContainer borderContainer2;
	private BTabbedPane main;
	private BSplitPane splitPane1;
	private BList words;
	private BorderContainer borderContainer5;
	private BSplitPane splitPane2;
	private BList list1;
	private BScrollPane scrollPane1;
	private BLabel label3;
	private BMenuBar menu;
	private BMenu file;
	private BMenuItem open;
	private BMenuItem save;

	/*************/
	public Jgrail() {

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File("lib/balise/interfaces.xml"));
			WidgetDecoder decoder = new WidgetDecoder(inputStream);
			window = (BFrame) decoder.getRootObject();
			menu = ((BMenuBar) decoder.getObject("Menu"));
			file = ((BMenu) decoder.getObject("File"));
			open = ((BMenuItem) decoder.getObject("Open"));
			save = ((BMenuItem) decoder.getObject("Save"));
			borderContainer2 = ((BorderContainer) decoder
					.getObject("BorderContainer2"));
			main = ((BTabbedPane) decoder.getObject("Main"));
			splitPane1 = ((BSplitPane) decoder.getObject("SplitPane1"));
			words = ((BList) decoder.getObject("Words"));
			borderContainer5 = ((BorderContainer) decoder
					.getObject("BorderContainer5"));
			splitPane2 = ((BSplitPane) decoder.getObject("SplitPane2"));
			list1 = ((BList) decoder.getObject("List1"));
			scrollPane1 = ((BScrollPane) decoder.getObject("ScrollPane1"));
			label3 = ((BLabel) decoder.getObject("Label3"));
			window.pack();
			window.setVisible(true);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
