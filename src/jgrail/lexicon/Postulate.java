package jgrail.lexicon;

import jpl.Term;

public class Postulate {

	private String name;
	private Term in;
	private Term out;

	public Postulate(String name, Term in, Term out) {
		this.name = name;
		this.in = in;
		this.out = out;
	
	}

	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return "postulate("+in+","+out+","+name+").\n";
	}
}
