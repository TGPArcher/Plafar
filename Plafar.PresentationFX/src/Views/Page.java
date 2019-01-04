package views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class Page {
	protected Parent contents = null;
	private String title = null;
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	protected abstract Parent doContents();
	
	protected Pane drawTitle() {
		Label titleLabel = new Label(getTitle());
		Separator titleSeparator = new Separator();
		
		return new VBox(titleLabel, titleSeparator);
	}
	
	public Parent getContents() {
		return contents;
	}
}
