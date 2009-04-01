package jgrail.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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

public class LexiconGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917769741613570279L;
	private JComponent mainPane;
	private JComponent listPane;
	private Component prefuseDisplay;
	private Component list1;
	private Component list2;
	private JMenuBar menu;
	private JMenu fileMenu;

	public LexiconGui() {
		super("Lexicon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
	}
	
	
	private void initialize() {
		setSize(300,300);
		setContentPane(getMainPane());
		setJMenuBar(getMainMenu());
	}


	private JMenuBar getMainMenu() {
		if(menu == null)
		{
			menu = new JMenuBar();
			menu.add(getFileMenu());
		}
		return menu;
	}
	
	private JMenu getFileMenu()
	{
		if(fileMenu == null)
		{
			fileMenu = new JMenu();
			JMenuItem m1 = new JMenuItem("Open");
			fileMenu.add(m1);
			m1 = new JMenuItem("Close");
			fileMenu.add(m1);
			m1 = new JMenuItem("New");
			fileMenu.add(m1);
		}
		return fileMenu;
	}


	private Container getMainPane() {
		if(mainPane == null)
		{
			mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getListPane(), getPrefuseDisplay());
			//mainPane.setLayout(new BorderLayout());
			//mainPane.add(getListPane(),BorderLayout.LINE_START);
			//mainPane.add(getPrefuseDisplay(), BorderLayout.CENTER);
		}
		return mainPane;
	}


	private Component getPrefuseDisplay() {
		if(prefuseDisplay == null)
		{
			prefuseDisplay = prefuse();
			prefuseDisplay.setSize(500,500);
		}
		return prefuseDisplay;
	}

	private Display prefuse()
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

	private Container getListPane() {
		if(listPane == null)
		{
			listPane = new JPanel();
			listPane.setLayout(new GridLayout(2,1));
			listPane.add(getList1());
			listPane.add(getList2());
			listPane.setSize(200, 200);
		}
		return listPane;
	}


	private Component getList2() {
		if(list2 == null)
		{
			list2 = new JList(new String[]{"Alice - NP","saw - V","Bob - NP"});
			
		}
		return list2;
	}


	private Component getList1() {
		if(list1 == null)
		{
			list1 = new JList(new String[]{"1","2","verylongstring"});
		}
		return list1;
	}
	
}
