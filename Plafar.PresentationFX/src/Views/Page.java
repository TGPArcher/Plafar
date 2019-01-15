package views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Page is the backbone of the view classes, responsible for creating the content, setting the title, drawing the title
 */
public abstract class Page {
	/**
	 * Here is the biggest and most important part of a page, the contents from it
	 */
	protected Parent contents = null;
	/**
	 * The page title
	 */
	private String title = null;
	
	/**
	 * This method is used to set the page title
	 * @param title - new title of the page
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * This method is used to retrieve the page title
	 * @return String - title of the page
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * This method is used to create the contents of a page
	 * @return Parent - the parent container of the page
	 */
	protected abstract Parent doContents();
	
	/**
	 * This method is used to draw the title of the page
	 * @return Pane - a container containing page title
	 */
	protected Pane drawTitle() {
		Label titleLabel = new Label(getTitle());
		Separator titleSeparator = new Separator();
		
		return new VBox(titleLabel, titleSeparator);
	}
	
	/**
	 * This method is used to retrieve the contents of a page, usually it is used by a page manager
	 * @return Parent - the parent container of the page
	 */
	public Parent getContents() {
		return contents;
	}
}