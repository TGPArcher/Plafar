package Views;

import javafx.scene.Parent;

public abstract class Page {
	private String title;
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public abstract Parent doContents();
}
