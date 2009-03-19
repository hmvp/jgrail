package jgrail.lexicon;

import jpl.Term;

public class PostulateFactory {

	public static Postulate parseEasyLexicon(String name, Term in, Term out) {
		return new Postulate(name, in, out);
	}

}
