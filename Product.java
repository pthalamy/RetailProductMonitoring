
import java.util.*;

public class Product {
	public String name;
	public String ASIN;
	public String url;
	public ArrayList<Offer> vendors = new ArrayList<Offer>();
	public Country country;
	public Double retailPrice;
	
	public Product(String ASIN, String url, Country country, Double price) {
		super();
		
		this.ASIN = ASIN;
		this.url = url;
		this.country = country;
		this.retailPrice = price;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("PRODUCT " + ASIN + " {\n");
		sb.append("\tname: " + name + "\n");
		sb.append("\turl: " + url + "\n");
		sb.append("\tcountry: " + country + "\n");
		sb.append("\tprice: " + retailPrice + "\n");
		sb.append("}");
		
		return sb.toString();
	}
}
