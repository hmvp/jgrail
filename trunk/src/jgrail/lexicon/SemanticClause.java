package jgrail.lexicon;

import jpl.Term;

public class SemanticClause {

	private Term term;

	public SemanticClause(Term term) {
		this.setTerm(term);
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Term getTerm() {
		return term;
	}

}
