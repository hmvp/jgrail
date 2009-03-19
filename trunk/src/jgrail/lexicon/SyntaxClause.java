package jgrail.lexicon;

import jpl.Term;

public class SyntaxClause{

	private Term term;

	public SyntaxClause(Term term) {
		this.setTerm(term);
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Term getTerm() {
		return term;
	}

}
