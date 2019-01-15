package util;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

/**
 * This is a utility class which provides methods to convert object to JSON and vice-vers
 */
public class JsonUtil {
	/**
	 * This method is used to retrieve an object from an JSON statement
	 * @param json - JSON data
	 * @param type - the class of the object on which to map the JSON
	 * @return T - the object of type containing the data from JSON
	 * @throws JsonSyntaxException
	 */
	public static <T> T fromJson(String json, Class<T> type) throws JsonSyntaxException {
		return new Gson().fromJson(json, type);
	}
	
	/**
	 * This method is used to retrieve an object from an JSON statement
	 * @param json - JSON data
	 * @param type - the type of the object on which to map the JSON
	 * @return T - the object of type containing the data from JSON
	 * @throws JsonSyntaxException
	 */
	public static <T> T fromJson(String json, Type type) throws JsonSyntaxException {
		return new Gson().fromJson(json, type);
	}
	
	/**
	 * This method is used to convert an java object to JSON
	 * @param object - the object to convert
	 * @return String - the JSON conversion of the object
	 */
	public static String toJson(Object object) {
	    return new Gson().toJson(object);
	}
	
	/**
	 * This method is used to convert an java object to a JSON element
	 * @param object - the object to convert
	 * @return JsonElement - a JSON element containing the data from object
	 */
	public static JsonElement toJsonTree(Object object) {
		return new Gson().toJsonTree(object);
	}
}