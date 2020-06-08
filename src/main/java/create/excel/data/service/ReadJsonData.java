package create.excel.data.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadJsonData {
	
	private String readAll(Reader reader) throws IOException {
		StringBuilder strBuilder = new StringBuilder();
		int readerCounter;
		while ((readerCounter = reader.read()) != -1) {
			strBuilder.append((char) readerCounter);
		}
		return strBuilder.toString();
	}
	
	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream inputStream = new URL(url).openStream();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String jsonText = this.readAll(bufferedReader);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			inputStream.close();
		}
	}
 
}
