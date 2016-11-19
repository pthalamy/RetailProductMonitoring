
import java.util.*;

public class Product {
	public String name;
	public String ASIN;
	public String url;
	public Country country;
	public Double retailPrice;
	public ArrayList<Offer> offers = new ArrayList<Offer>();
	
	public Product(String name, String ASIN, String url, Country country) {
		super();

		this.name = name;
		this.ASIN = ASIN;
		this.url = url;
		this.country = country;
	}

	public void printCSDescription() {
		System.out.println("\tCS Product: " + name + "; ASIN: " + ASIN + "; From: " + country + "; price: " + retailPrice);
		System.out.println("\tCS URL: " + url);		
	}

	public void setRetailPrice(Double price) {
		this.retailPrice = price;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Product " + ASIN + " {\n");
		sb.append("\tname: " + name + "\n");
		sb.append("\turl: " + url + "\n");
		sb.append("\tcountry: " + country + "\n");
		sb.append("\tprice: " + retailPrice + "\n");
		sb.append("\toffers: \n");

		for (Offer offer : this.offers) {
			sb.append(offer);
		}

		sb.append("\n}");

		return sb.toString();
	}
}
