package theego.RestClient;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Delete {

	public static void main(String[] args) {
		String urll = "http://localhost:8888/tk/delete/20";
		
		try {
			URL url = new URL(urll);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("DELETE");
			
			InputStream in = new BufferedInputStream(conn.getInputStream());
			in.close();
			//String result = IOUtils.toString(in , "UTF-8");
		} catch (Exception e) {
		}
	}
}
