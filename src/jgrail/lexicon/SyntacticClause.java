package jgrail.lexicon;

import prefuse.data.Node;
import prefuse.data.Tree;
import jpl.Compound;
import jpl.Term;

public class SyntacticClause{

	private Operator term;

	public SyntacticClause(Operator operator) {
		this.setTerm(operator);
	}

	public void setTerm(Operator operator) {
		this.term = operator;
	}

	public Term getTerm() {
		return term.getTerm();
	}

	public Tree getTree()
	{
		Tree t = new Tree();
		
		Node root = t.addRoot();
        root.getTable().addColumn("name", String.class);
		
        term.getTree(t, root);
        
		return t;
	}
}



abstract class Operator {
	abstract public Term getTerm();
	abstract public void getTree(Tree t, Node root);
	protected String name;

	public Operator(String name) {
		this.name = name;
	}
}

class Atom extends Operator
{
	public Atom(String name) {
		super(name);
	}

	@Override
	public jpl.Atom getTerm() {
		return new jpl.Atom(name);
	}

	@Override
	public void getTree(Tree t, Node root) {
		t.addChild(root).set("name", name);
	}
	
}

class Binary extends Operator 
{
	protected String modality;
	private Operator left;
	private Operator right;
	
	public Binary(String mod, String name, Operator left, Operator right) {
		super(name);
		this.modality = mod;
		this.left = left;
		this.right = right;
	}

	@Override
	public Term getTerm() {
		return new Compound(name,new Term[] {new jpl.Atom(modality),left.getTerm(),right.getTerm()});
	}

	@Override
	public void getTree(Tree t, Node root) {
		Node n = t.addChild(root);
		n.set("name", name);
		left.getTree(t, n);
		right.getTree(t, n);
	}	
}

class DRight extends Binary 
{
	public DRight(String mod, Operator left, Operator right) {
		super(mod, "dr", left, right);
	}
}

class DLeft extends Binary 
{
	public DLeft(String mod, Operator left, Operator right) {
		super(mod, "dl", left, right);
	}
}