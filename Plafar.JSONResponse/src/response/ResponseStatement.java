package response;

import com.google.gson.JsonElement;

public class ResponseStatement {
	private int status;
	private String errorMessage;
	private JsonElement data;
	
	public ResponseStatement(int status) {
		this.status = status;
	}
	
	public ResponseStatement(int status, JsonElement data) {
		this.status = status;
		this.data = data;
	}
	
	public ResponseStatement(int status, String errorMessage) {
		this.status = status;
		this.errorMessage = errorMessage;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public JsonElement getData() {
		return data;
	}
}
