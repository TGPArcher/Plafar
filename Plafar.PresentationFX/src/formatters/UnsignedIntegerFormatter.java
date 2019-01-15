package formatters;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

/**
 * UnsignedIntegerFormatter is a factory class creating TextFormmaters with unsigned numbers
 */
public final class UnsignedIntegerFormatter {
	
	/**
	 * @return TextFormatter - a text formatter which accepts only unsigned numbers
	 */
	public static TextFormatter<String> getFormatter(){
		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
		    if (newText.matches("\\d*?")) { 
		        return change;
		    }
		    return null;
		};
	     
	     return new TextFormatter<String>(filter);
	}
	
	/**
	 * @param max - the maximum number (inclusive) accepted by the formatter
	 * @return TextFormatter - a text formatter which accepts only unsigned numbers smaller than max
	 */
	public static TextFormatter<String> getFormatter(int max){
		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
		    if (newText.matches("\\d*?")) {
		    	try {
		    		if(Integer.parseInt(newText) <= max) {
			    		return change;
			    	}
		    	}
		    	catch(NumberFormatException e){
		    		change.setText("");
		    		return change;
		    	}
		    }
		    return null;
		};
	     
	     return new TextFormatter<String>(filter);
	}
	
	/**
	 * @param min - the minimum number (inclusive) accepted by the formatter
	 * @param max - the maximum number (inclusive) accepted by the formatter
	 * @return TextFormatter - a text formatter which accepts only unsigned numbers smaller than max and bigger than min
	 */
	public static TextFormatter<String> getFormatter(int min, int max){
		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
		    if (newText.matches("\\d*?")) {
		    	try {
		    		int newNr = Integer.parseInt(newText);
			    	if(newNr >= min && newNr <= max) {
			    		return change;
			    	}
		    	}
		    	catch(NumberFormatException e) {
		    		change.setText("");
		    		return change;
		    	}
		    }
		    return null;
		};
	     
	     return new TextFormatter<String>(filter);
	}
}