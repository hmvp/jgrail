package jgrail.gui;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jgrail.JGrail;
import jgrail.lexicon.Lexicon;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Node;
import prefuse.data.Tree;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import buoy.event.WindowClosingEvent;
import buoy.widget.AWTWidget;
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

public class ParserGUI {

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
	private JGrail jg;

	/*************/

	public ParserGUI(JGrail jg) {
		this.jg = jg;

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File("lib/balise/interfaces/Main.xml"));
			WidgetDecoder decoder = new WidgetDecoder(inputStream);
			window = (BFrame) decoder.getRootObject();
			window.addEventLink(WindowClosingEvent.class, window, "dispose");

			menu = ((BMenuBar) decoder.getObject("Menu"));
			file = ((BMenu) decoder.getObject("File"));
			open = ((BMenuItem) decoder.getObject("Open"));
			save = ((BMenuItem) decoder.getObject("Save"));
			borderContainer2 = ((BorderContainer) decoder
					.getObject("BorderContainer2"));
			main = ((BTabbedPane) decoder.getObject("Main"));
			splitPane1 = ((BSplitPane) decoder.getObject("SplitPane1"));
			AWTWidget widget = new AWTWidget(prefuseWidget());
			splitPane1.add(widget, 1);
			words = ((BList) decoder.getObject("Words"));

			splitPane2 = ((BSplitPane) decoder.getObject("SplitPane2"));
			list1 = ((BList) decoder.getObject("List1"));
			populateList(list1);
			
			
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

	private Component prefuseWidget() 
		{
	        // -- 1. load the data ------------------------------------------------
	        
	        // load the socialnet.xml file. it is assumed that the file can be
	        // found at the root of the java classpath
	 
	    	
	    	
	    	
	        Tree t = new Tree();
	        Node root = t.addRoot();
	        root.getTable().addColumn("gender", String.class);
	        root.getTable().addColumn("name", String.class);

	        Node c = t.addChild(root);
	        Node c1 = t.addChild(root);
	        Node c2 = t.addChild(c1);
	        Node c3 = t.addChild(c1);
	        
	        
	        
	        c.set("gender", "F");
	        root.set("gender", "F");
	        c.set("name", "Test");
	        root.set("name", "Root");
	        c1.set("gender", "M");
	        c1.set("name", "Test1");
	        c2.set("gender", "M");
	        c2.set("name", "Test2");
	        c3.set("gender", "F");
	        c3.set("name", "Test3");
	        
	        
	        
	        // -- 2. the visualization --------------------------------------------
	        
	        // add the graph to the visualization as the data group "graph"
	        // nodes and edges are accessible as "graph.nodes" and "graph.edges"
	        Visualization vis = new Visualization();
	        vis.addTree("tree", t);
	        vis.setInteractive("tree.edges", null, false);
	        
	        // -- 3. the renderers and renderer factory ---------------------------
	        
	        // draw the "name" label for NodeItems
	        LabelRenderer r = new LabelRenderer("name");
	   
	        //r.setRoundedCorner(8, 8); // round the corners
	       // LabelRenderer r2 = new LabelRenderer("gender");
	        
	        // create a new default renderer factory
	        // return our name label renderer as the default for all non-EdgeItems
	        // includes straight line edges for EdgeItems by default
	        vis.setRendererFactory(new DefaultRendererFactory(r));
	        
	        
	        // -- 4. the processing actions ---------------------------------------
	        
	        // create our nominal color palette
	        // pink for females, baby blue for males
	        int[] palette = new int[] {
	            ColorLib.rgb(255,180,180), ColorLib.rgb(190,190,255)
	        };
	        // map nominal data values to colors using our provided palette
	        DataColorAction fill = new DataColorAction("tree.nodes", "gender",
	              Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
	        // use black for node text
	        ColorAction text = new ColorAction("tree.nodes",
	                VisualItem.TEXTCOLOR, ColorLib.gray(0));
	        // use light grey for edges
	        ColorAction edges = new ColorAction("tree.edges",
	                VisualItem.STROKECOLOR, ColorLib.gray(200));
	        
	        // create an action list containing all color assignments
	        ActionList color = new ActionList();
	        color.add(fill);
	        color.add(text);
	        color.add(edges);
	        
	        // create an action list with an animated layout
	        ActionList layout = new ActionList(Activity.INFINITY);
	        NodeLinkTreeLayout l = new NodeLinkTreeLayout("tree");
	        l.setOrientation(Constants.ORIENT_TOP_BOTTOM);
	        
	        layout.add(l);
	        layout.add(new RepaintAction());
	        
	        // add the actions to the visualization
	        vis.putAction("color", color);
	        vis.putAction("layout", layout);
	        
	        
	        // -- 5. the display and interactive controls -------------------------
	        
	        Display d = new Display(vis);
	        d.setSize(720, 500); // set display size
	        // drag individual items around
	        d.addControlListener(new DragControl());
	        // pan with left-click drag on background
	        d.addControlListener(new PanControl()); 
	        // zoom with right-click drag
	        d.addControlListener(new ZoomControl());
	        
	 
	        // assign the colors
	        vis.run("color");
	        // start up the animated layout
	        vis.run("layout");
	        
	        return d;
		}

	
	

	private void populateList(BList list12) {
		Lexicon l = jg.getLexicon();
		l.addObserver(new LexiconList(list12));
		
	}
}
