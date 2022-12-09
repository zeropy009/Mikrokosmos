package theego.RestClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import theego.model.TaiKhoan;

/**
 * Hello world!
 *
 */
public class GetAll 
{	
    public static void main( String[] args )
    {
    	try {
    		URL url = new URL("http://localhost:8888/tk/getall");
    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    		connection.setRequestMethod("GET");
    	
    		StringBuffer buffer = new StringBuffer();
    		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    		String line;
    		while((line = reader.readLine()) != null) {
    			buffer.append(line);
    		}
    		
    		abc(buffer.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public static void abc(String requestBody) {
    	JSONArray array = new JSONArray(requestBody);
    	
    	List<TaiKhoan> list = new ArrayList<TaiKhoan>();
    	for(int i = 0; i < array.length(); i++) {
    		JSONObject obj = array.getJSONObject(i);
    		int id = obj.getInt("id");
    		String password = obj.getString("password");
    		String name = obj.getString("name");
    		TaiKhoan taiKhoan = new TaiKhoan(id, password, name);
    		list.add(taiKhoan);
    	}
    	
    	for(int i = 0; i < list.size(); i++) {
    		System.out.println("id: " + list.get(i).getId());
    		System.out.println("password: " + list.get(i).getPassword());
    		System.out.println("name: " + list.get(i).getName());
    		System.out.println("=====================");
    	}
	}
}
