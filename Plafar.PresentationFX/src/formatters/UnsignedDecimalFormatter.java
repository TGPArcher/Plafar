package formatters;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;

/**
 * UnsignedDecimalFormatter is a factory class creating TextFormatters for decimal numbers
 */
public class UnsignedDecimalFormatter {
	/**
	 * @return TextFormatter - a text formatter which accepts only decimal numbers with two decimals after point
	 */
	public static TextFormatter<String> getFormatter(){
		UnaryOperator<TextFormatter.Change> filter = change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*\\.?\\d{0,2}$")) {
				return change;
			}
			
			return null;
		};
		
		return new TextFormatter<String>(filter);
	}
}