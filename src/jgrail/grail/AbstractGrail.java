/**
 * 
 */
package jgrail.grail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import jgrail.lexicon.Lexicon;
import jgrail.sentence.Sentence;
import jpl.JPL;
import jpl.Query;
import jpl.Term;

/**
 * @author hiram
 *
 */
public abstract class AbstractGrail implements Grail {
	
	private Lexicon lexicon;
	private File temp;

	/**
	 * @see jgrail.grail.Grail#init()
	 */
	public void init() {
		JPL.setNativeLibraryDir("/opt/local/lib/swipl-5.6.63/lib/i386-darwin9.5.0");
		Query.hasSolution("['prolog/grail'],reset_statistics(on).");
	}

	/**
	 * @see jgrail.grail.Grail#loadLexicon(jgrail.lexicon.Lexicon)
	 */
	public void loadLexicon(Lexicon lex) {
		 try {
			temp = File.createTempFile("lexicon", ".pl");
			//temp.deleteOnExit();
			FileWriter fw = new FileWriter(temp);			
			fw.write(lex.toString());
			fw.close();
			
			//Query.hasSolution("load_fragment('"+temp.getAbsoluteFile()+"').");
			
			this.lexicon = lex;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws Exception 
	 * @see jgrail.grail.Grail#parseSentence(jgrail.sentence.Sentence)
	 */
	@SuppressWarnings("unchecked")
	public List<Term> parseSentence(Sentence s) throws Exception {
		for(String word : s.getTokens())
		{
			if(lexicon.containsLexeme(word))
				throw new Exception(word + " is niet gevonden in het lexicon!");
		}
		
		List<Term> l = new ArrayList<Term>();
		String query = "load_fragment('/Users/hiram/Documents/workspace/JGrail/lib/fraginput.pl'),parse("+s.getParsedSentence()+","+s.getFormula().getTerm()+").";
		Query q1 = new Query(query);
		System.out.println(query);
		Hashtable<String, Term>[] results = q1.allSolutions();
		
		for(Hashtable<String, Term> h: results){
			Term t = h.get("X");
			l.add(t);
		}
		return l;
	}

	/**
	 * @see jgrail.grail.Grail#parseSentences(jgrail.sentence.Sentence[])
	 */
	public List<List<Term>> parseSentences(Sentence[] sl) {
		List<List<Term>> result = new ArrayList<List<Term>>(sl.length);
		for(Sentence s : sl)
		{
			try {
				result.add(parseSentence(s));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
