
import org.json.*;
import java.util.*;
import java.io.*;

public class ProductMonitoring {

	public static void main(String[] args) {
		try {
			String jsonData = readFile("run_results.json");
			JSONObject obj = new JSONObject(jsonData);
			String ASIN = obj.getString("search_bar");
			String sellersUrl = obj.getString("sellers_link");
			JSONArray companies = obj.getJSONArray("companyName");
			Product p1 = new Product(ASIN, sellersUrl, Country.deduceFromUrl(sellersUrl), 0.0);

			System.err.println(p1);
			
			for (int i = 0; i < companies.length(); i++)	{
				String companyName = companies.getJSONObject(i).getString("name");
				String sellerUrl = companies.getJSONObject(i).getString("url");
				String price = companies.getJSONObject(i).getString("price");
				String expFrom = companies.getJSONObject(i).getString("expFrom");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static String readFile(String filename) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
