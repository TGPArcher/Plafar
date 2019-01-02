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
}
