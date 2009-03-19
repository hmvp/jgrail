package jgrail.grail;

import java.util.List;

import jgrail.lexicon.Lexicon;
import jgrail.sentence.Sentence;
import jpl.Term;

public interface Grail {

	public void init();
	public void loadLexicon(Lexicon l);
	public List<Term> parseSentence(Sentence s) throws Exception;
	public List<List<Term>> parseSentences(Sentence[] sl);
}
