package jgrail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import jgrail.grail.Grail;
import jgrail.grail.Grail2;
import jgrail.gui.ParserGUI;
import jgrail.lexicon.Lexicon;
import jgrail.lexicon.LexiconFactory;
import jgrail.sentence.Sentence;
import jgrail.sentence.SentenceFactory;
import jpl.Term;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;

public class JGrail {
	private JSAPResult config;
	private Grail grail;
	private Lexicon lexicon = new Lexicon();

	public static void main(String[] args) throws JSAPException, MalformedURLException, IOException {
		JGrail jg = new JGrail();
		jg.setArguments(args);
		jg.init();
		jg.run();
		ParserGUI x1 = new ParserGUI(jg);
	}

	private void init() {
		grail = new Grail2();
		grail.init();
		
		if(!config.contains("lexicon"))
		{
			lexicon = new Lexicon();
		} 
		else if (config.contains("lexicon"))
		{
			try {
				lexicon = LexiconFactory.parseEasyLexicon(lexicon, config.getFile("lexicon"));
				//grail.init();
				grail.loadLexicon(lexicon);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(config.contains("sentence"))
		{
			Sentence s= SentenceFactory.parseStringToSentence(config.getString("sentence"));
			List<Term> result;
			try {
				result = grail.parseSentence(s);
				System.out.println(result.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//grail.init();
	}

	private void run() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	private void setArguments(String[] args) throws JSAPException, MalformedURLException, IOException {
		JSAP jsap = new JSAP(new URL("file","localhost","lib/CommandlineOptions.xml"));

		config = jsap.parse(args);
		
		if(!config.success())
		{
			System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help.  This is called "beating the user with a clue stick."
            for (java.util.Iterator errs = config.getErrorMessageIterator();
                    errs.hasNext();) {
                System.err.println("Error: " + errs.next());
            }
		}
		if (!config.success() || config.getBoolean("help"))
		{
            System.err.println();
            System.err.print("Usage: jgrail ");
            System.err.println(jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.exit(1);
		} 
	}

	public Lexicon getLexicon() {
		return lexicon;
	}
}
