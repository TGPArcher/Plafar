package util;

import spark.ResponseTransformer;

public class JsonUtilSpark extends JsonUtil {
	
	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
}
