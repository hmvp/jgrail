package test;

import jpl.Query;

public class TestJPL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Query q = new Query("['prolog/grail.pl']");
		q.hasSolution();
		Query q1 = new Query("reset_statistics(on),load_fragment(tmpfrag), tokenize(\"\",List).");
		q1.hasSolution();
	}

}
