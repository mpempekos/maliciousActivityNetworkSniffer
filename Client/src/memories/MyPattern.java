package memories;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPattern {
	
	//////////////////////////////////////////////////////////////////////////////
	/// takes payload --> ignores hex garbage --> searching the malicious word ///
	
	public static int searchMaliciousPatterns(String word, String payload) {
		
		Pattern pattern = Pattern.compile(word);

		StringBuilder fixedPayload = new StringBuilder();
		String fixedLine = new String();
		String[] lines = payload.split(System.getProperty("line.separator"));		
		
		for (int i=0; i<lines.length; i++) {
			// ignoring first 60 chars (hex garbage) of every line
			fixedLine = lines[i].replaceAll("^.{60}\\s*", "");
			fixedPayload.append(fixedLine);
		}
		//System.out.println("Fixed: \n" + fixedPayload);
		Matcher matcher = pattern.matcher(fixedPayload.toString());
		int matches = 0;
		while (matcher.find()) {
			matcher.group();
			matcher.start();
			matcher.end();
			matches++;
		}		
		return matches;
	}
	
}
