package jgrail.sentence;

import jgrail.lexicon.SyntacticClause;

public class Sentence {
	private String parsedSentence;
	private String[] tokens;
	private String originalSentence;
	private SyntacticClause formula;
	
	/**
	 * Construct a new sentence from a string
	 * @param sentence
	 */
	protected Sentence(String[] tokens, String originalSentence, SyntacticClause formula){
		this.tokens = tokens;
		this.originalSentence = originalSentence;
		this.formula = formula;
		parsedSentence = parseSentence();
	}
	
	/**
	 * Parse a string to an prolog term (a list containing the words)
	 * @param sentence
	 * @return
	 */
	private String parseSentence()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for(String token :tokens)
		{
			sb.append(token).append(',');
		}
		sb.deleteCharAt(sb.length()-1).append(']');
		return sb.toString();
	}

	/**
	 * @return the parsedSentence
	 */
	public String getParsedSentence() {
		return parsedSentence;
	}

	public SyntacticClause getFormula() {
		return formula;
	}

	/**
	 * @return the tokens
	 */
	public String[] getTokens() {
		return tokens;
	}

	public String getOriginalSentence() {
		return originalSentence;
	}
	
	
	
	
}
