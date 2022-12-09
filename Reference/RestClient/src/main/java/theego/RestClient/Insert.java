package theego.RestClient;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Insert {

	public static void main(String[] args) {
		String urll = "http://localhost:8888/tk/add";
		
		JSONObject json = new JSONObject();
		json.put("password", "456123");
		json.put("name", "Ai biết");
		
		try {
			URL url = new URL(urll);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			
			OutputStream os = conn.getOutputStream();
			os.write(json.toString().getBytes("UTF-8"));
			os.close();
			
			InputStream in = new BufferedInputStream(conn.getInputStream());
			in.close();
			//String result = IOUtils.toString(in , "UTF-8");
		} catch (Exception e) {
		}
	}
}
