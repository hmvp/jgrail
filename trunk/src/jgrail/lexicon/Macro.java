package jgrail.lexicon;

public class Macro {

	private String word;
	private SyntaxClause syn;

	public Macro(String word, SyntaxClause syn) {
		this.word = word;
		this.syn = syn;
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @return the syn
	 */
	public SyntaxClause getSyn() {
		return syn;
	}

	public String name() {
		return getWord();
	}
	
	@Override
	public String toString() {
		return "macro("+word+","+syn.getTerm()+").\n";
		
	}

}
