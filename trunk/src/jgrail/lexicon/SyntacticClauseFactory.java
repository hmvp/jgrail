package jgrail.lexicon;

import jpl.Atom;
import jpl.Compound;
import jpl.Term;



public class SyntacticClauseFactory {

	public static SyntacticClause parseEasyLexicon(Term term) {
		return new SyntacticClause(parseTerm(term));
	}



	private static Operator parseTerm (Term t)
	{
		if (t instanceof Atom) {
			Atom a = (Atom) t;
			
			return new jgrail.lexicon.Atom(a.name());
		} else if (t instanceof Compound) {
			Compound c = (Compound) t;
			String mod = ((Atom) c.arg(1)).name();
			String name = c.name();
			if (name == "dr") {
				return new DRight(mod, parseTerm(c.arg(2)), parseTerm(c.arg(3)) );
			} else {
				return new DLeft(mod, parseTerm(c.arg(2)), parseTerm(c.arg(3)) );
			}
		} else {
			throw new RuntimeException("oowjee");
		}
	}



	public static SyntacticClause simpleClause(String string) {
		return new SyntacticClause(new jgrail.lexicon.Atom(string));
	}
}