package theego.RestClient;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class FindOne {

	public static void main(String[] args) {
		String urll = "http://localhost:8888/tk/3";

		try {
			URL url = new URL(urll);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			InputStream in = new BufferedInputStream(conn.getInputStream());

			String result = IOUtils.toString(in, "UTF-8");
			System.out.println(result);

			JSONArray array = new JSONArray(result.replace("{", "[{").replace("}", "}]"));
			JSONObject obj = array.getJSONObject(0);
			System.out.println("id: " + obj.getInt("id"));
			System.out.println("password: " + obj.getString("password"));
			System.out.println("name: " + obj.getString("name"));

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
