package response;

import com.google.gson.JsonElement;

/**
 * ResponseStatement is a class which is used as an response template, containing the status of the operation, the errorMessage and the data.
 * This class is used to be a standard type of response between processes
 */
public class ResponseStatement {
	/**
	 * The response's status code
	 */
	private int status;
	/**
	 * The response's errorMessage
	 * Note: can be null or empty on success
	 */
	private String errorMessage;
	/**
	 * The response's data as JSON
	 */
	private JsonElement data;
	
	/**
	 * Initializes an ResponseStatement containing only the status of operation.
	 * Usually used on successful operations
	 * @param status - the status code of the operation
	 */
	public ResponseStatement(int status) {
		this.status = status;
	}
	
	/**
	 * Initializes an ResponseStatement containing status code and data of the operation.
	 * Usually used on successful operations which include data retrieval
	 * @param status - the status code of the operation
	 * @param data - the data returned by the operation in JSON format
	 */
	public ResponseStatement(int status, JsonElement data) {
		this.status = status;
		this.data = data;
	}
	
	/**
	 * Initializes an ResponseStatement containing status code and errorMessage of the operation.
	 * Usually used on operation failure
	 * @param status - the status code of the operation
	 * @param errorMessage - the error message of the operation
	 */
	public ResponseStatement(int status, String errorMessage) {
		this.status = status;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * This method is used to retrieve response's status code
	 * @return int - status code
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * This method is used to retrieve response's error message
	 * @return String - the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	 /**
	  * This method is used to retrieve response's data
	  * @return JsonElement - a JsonElement containing the data
	  */
	public JsonElement getData() {
		return data;
	}
}