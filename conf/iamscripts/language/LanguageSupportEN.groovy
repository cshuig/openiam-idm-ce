
import org.openiam.ui.util.LanguageSupport;


public class LanguageSupportEN implements LanguageSupport   {

	public String addArticle(String word) {
		System.out.println("LanguageSupportEN script called with 'addArticle' command");
		if(word != null && word.length() > 0) {
			String article = " a ";
	            	char[] vowels="aeiouAEIOU".toCharArray();
	            	char firstLetter = word.charAt(0);
	            	for(char ch: vowels){
	                	if(ch == firstLetter){
	                	    article = " an ";
	                	    break;       
	                	}
	            	}
	            	return article + word;
		} else {
			return null;
		}
	}

}
