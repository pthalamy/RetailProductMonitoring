
import org.json.*;
import java.util.*;
import java.io.*;

public class ProductMonitoring {

	public static void main(String[] args) {
		try {
			// Load JSON Data
			String jsonData = readFile("run_results.json");
			JSONObject obj = new JSONObject(jsonData);

			// Initalize products container
			ArrayList<Product> products = new ArrayList<Product>();
			
			// Parse JSON Data for each product
			JSONArray jsonProducts = obj.getJSONArray("products");
			for (int i = 0; i < jsonProducts.length(); i++) {
				String name = jsonProducts.getJSONObject(i).getString("name");
				String asin = jsonProducts.getJSONObject(i).getString("ASIN");
				String offersUrl = jsonProducts.getJSONObject(i).getString("offers_url");

				Product p1 = new Product(name, asin, offersUrl, Country.deduceFromUrl(offersUrl), 0.0);
				products.add(p1);
				
				JSONArray offers = jsonProducts.getJSONObject(i).getJSONArray("offers");
				for (int j = 0; j < offers.length(); j++)	{
					String companyName = offers.getJSONObject(j).getString("name");
					String companyUrl = offers.getJSONObject(j).getString("url");
					String price = offers.getJSONObject(j).getString("price");
					String expFrom = offers.getJSONObject(j).getString("expFrom");

					Offer co = new Offer(companyName, companyUrl, Country.expFromStringToCountry(expFrom), price);
					p1.offers.add(co);
				}

			}

			printAllProducts(products);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//! Converts file at path filename into a String
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

	public static void printAllProducts(ArrayList<Product> products) {
		for (Product p : products) {
			System.out.println(p);
		}
	}
}
