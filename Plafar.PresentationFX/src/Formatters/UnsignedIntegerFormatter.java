package Formatters;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

public final class UnsignedIntegerFormatter {
	
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
