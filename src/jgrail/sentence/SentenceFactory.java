package jgrail.sentence;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

import jgrail.lexicon.SyntaxClause;

import jpl.Atom;

public class SentenceFactory {
	
	public static Sentence parseStringToSentence(String string)
	{
		StringReader sr = new StringReader(string);
		StreamTokenizer sTokenizer = new StreamTokenizer(sr);
		
		//setup
		ArrayList<String> list = new ArrayList<String>();
		try {
			while (sTokenizer.nextToken() != StreamTokenizer.TT_EOF)
			{
				if(sTokenizer.ttype == StreamTokenizer.TT_WORD || sTokenizer.ttype == '"')
					list.add(sTokenizer.sval);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Sentence(list.toArray(new String[list.size()]), string, new SyntaxClause(new Atom("np")));
		
	}


}
