package util;

import spark.ResponseTransformer;

/**
 * This is a extension to the JsonUtil class which adds more JSON functionality targeted to be used by the Spark server
 */
public class JsonUtilSpark extends JsonUtil {
	
	/**
	 * This method is used to get the reference of the toJson method from JsonUtil
	 * This method is designed to be used by Spark's server to convert the response to JSON
	 * @return ResponseTransformer - a reference to toJson method from JsonUtil
	 */
	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
}
