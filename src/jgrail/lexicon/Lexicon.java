package jgrail.lexicon;

import java.io.File;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Set;

public class Lexicon extends Observable{
	
	Hashtable<String, Lexeme> lexemes = new Hashtable<String, Lexeme>();
	Hashtable<String, Macro> macros = new Hashtable<String, Macro>();
	Hashtable<String, Postulate> postulates = new Hashtable<String, Postulate>();

	

	public Lexicon() {
		// TODO Auto-generated constructor stub
	}
	
	public Lexicon(File file) {
		// TODO Auto-generated constructor stub
		notifyObservers();
	}

	public void addLexeme(Lexeme lm) {
		lexemes.put(lm.name(), lm);
		notifyObservers();
	}

	public void addMacro(Macro macro) {
		macros.put(macro.name(), macro);
		notifyObservers();
	}
	
	public void addPostulate(Postulate post) {
		postulates.put(post.name(), post);	
		notifyObservers();
	}

	public boolean containsLexeme(String word) {
		return lexemes.contains(word);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for(Lexeme lex: lexemes.values())
		{
			sb.append(lex.toString());
		}
		for(Macro macro: macros.values())
		{
			sb.append(macro.toString());
		}
		for(Postulate post: postulates.values())
		{
			sb.append(post.toString());
		}
		
		
		
		return sb.toString();
	}
	
	public Set<String> getLexemes()
	{
		return lexemes.keySet();
	}
	
}
