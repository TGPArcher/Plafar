package Views;

import javafx.scene.Parent;

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
	
	public Parent getContents() {
		return contents;
	}
}
