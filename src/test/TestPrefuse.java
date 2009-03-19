package test;

import javax.swing.JFrame;

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

public class TestPrefuse {

    public static void main(String[] argv) {
        
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
        
        // -- 6. launch the visualization -------------------------------------
        
        // create a new window to hold the visualization
        JFrame frame = new JFrame("prefuse example");
        // ensure application exits when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(d);
        frame.pack();           // layout components in window
        frame.setVisible(true); // show the window
        
        // assign the colors
        vis.run("color");
        // start up the animated layout
        vis.run("layout");
    }
    
}
