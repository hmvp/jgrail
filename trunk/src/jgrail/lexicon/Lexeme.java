package jgrail.lexicon;


public class Lexeme {
	private String word;
	private SemanticClause sem;
	private SyntaxClause syn;
	//private PhoneticClause phon;

	
	
	public Lexeme(String lexeme)
	{
		parseLexemeString(lexeme);
	}


	private void parseLexemeString(String lexeme) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @param word
	 * @param syn
	 * @param sem
	 */
	public Lexeme(String word, SyntaxClause syn, SemanticClause sem) {
		this.word = word;
		this.sem = sem;
		this.syn = syn;
	}


	public String name() {
		return getWord();
	}


	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}


	/**
	 * @return the sem
	 */
	public SemanticClause getSem() {
		return sem;
	}


	/**
	 * @return the syn
	 */
	public SyntaxClause getSyn() {
		return syn;
	}
	
	@Override
	public String toString() {
		return "lex("+word+","+syn.getTerm()+","+sem.getTerm()+").\n";
	}
}
