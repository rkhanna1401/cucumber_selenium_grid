package Utils;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonUtils {

	public static String getValFromJson(JsonObject json, String dataKey, String defaultValue) {
		JsonElement element = json.get(dataKey);
		String value;

		if (element == null) {
			value = defaultValue;
		} else {
			value = element.getAsString();
		}

		return value;
	}

	public static String getValFromJson(JsonObject json, String dataKey) {
		JsonElement element = json.get(dataKey);
		String value = element.getAsString();
		return value;
	}

	public static String getConfigsJsonFilePath(String fileName) {
		String path = System.getProperty("user.dir")+"/configs/"+ fileName +".json";
		return path;
	}

	@SuppressWarnings("deprecation")
	public static JsonObject SetupJsonConfig(String jsonPath) throws Exception{
		File file = new File(jsonPath);

		if (!file.exists()) {
			throw new Exception("Could not find the specified json path.");
		}

		Reader jsFileReader = new FileReader(file);
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(jsFileReader).getAsJsonObject();
	}

}
