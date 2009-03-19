package jgrail.util;

import java.io.File;

import com.martiansoftware.jsap.ParseException;
import com.martiansoftware.jsap.StringParser;

public class FileStringParser extends StringParser {

	@Override
	public File parse(String arg0) throws ParseException {
		return new File(arg0);
	}

}
