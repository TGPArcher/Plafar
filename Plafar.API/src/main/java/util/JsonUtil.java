package util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import spark.ResponseTransformer;

public class JsonUtil {
	
	public static <T> T fromJson(String json, Class<T> type) throws JsonSyntaxException {
		return new Gson().fromJson(json, type);
	};
	
	public static String toJson(Object object) {
	    return new Gson().toJson(object);
	}
	
	public static JsonElement toJsonTree(Object object) {
		return new Gson().toJsonTree(object);
	}
	 
	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
}
