package jgrail.lexicon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;

import jpl.Query;
import jpl.Term;


public class LexiconFactory {
	@SuppressWarnings("unchecked")
	public static Lexicon parseEasyLexicon(Lexicon l, File file) throws FileNotFoundException
	{
		Query q = new Query("['lib/inputcat.pl'],['"+file.getAbsolutePath()+"'].");
		q.hasSolution();
		
		
		Hashtable<String,Term>[] lexemes = Query.allSolutions("lex(Word,Syn,Sem)");
		for(Hashtable<String,Term> lexeme : lexemes)
		{
			SyntacticClause syn = SyntacticClauseFactory.parseEasyLexicon(lexeme.get("Syn"));
			SemanticClause sem = SemanticClauseFactory.parseEasyLexicon(lexeme.get("Sem"));
			Lexeme lm = new Lexeme(lexeme.get("Word").toString(), syn, sem);
			l.addLexeme(lm);
		}
		
		Hashtable<String,Term>[] macros = Query.allSolutions("macro(Name,Syn)");
		for(Hashtable<String,Term> macro : macros)
		{
			SyntacticClause syn = SyntacticClauseFactory.parseEasyLexicon(macro.get("Syn"));
			Macro mc = new Macro(macro.get("Name").toString(), syn);
			l.addMacro(mc);
		}
		
		Hashtable<String,Term>[] postulates = Query.allSolutions("postulate(In, Out, Name)");
		for(Hashtable<String,Term> postulate : postulates)
		{
			Postulate post = PostulateFactory.parseEasyLexicon(postulate.get("Name").toString(),postulate.get("In"),postulate.get("Out"));
			l.addPostulate(post);
		}
		
		q.rewind();
		return l;
	}
}
