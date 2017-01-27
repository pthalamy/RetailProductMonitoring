
import java.util.*;

public class Product {
	public String name;
	public String ASIN;
	public Brand brand;
	public Country country;
	public Double retailPrice;
	public ArrayList<Offer> offers = new ArrayList<Offer>();
	
	public Product(String name, Brand brand, double retailPrice, String ASIN) {
		super();

		this.name = name;
		this.brand = brand;
		this.retailPrice = retailPrice;
		this.ASIN = ASIN;
	}

	public void printCSDescription() {
		// System.out.println("\tProduct: " + name + "; ASIN: " + ASIN + "; Store: " + country + "; price: " + retailPrice);
		// System.out.println("\tURL: " + url);		
	}

	public void setRetailPrice(Double price) {
		this.retailPrice = price;
	}

	public String getUrlForCountry(Country country) {
		return "https://www.amazon."
			+ this.country.getExtension()
			+ "/gp/offer-listing/"
			+ this.ASIN
			+ "/ref=dp_olp_new?ie=UTF8&condition=new";
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Product " + ASIN + " {\n");
		sb.append("\tname: " + name + "\n");
		// sb.append("\tcountry: " + country + "\n");
		sb.append("\tprice: " + retailPrice + "\n");
		sb.append("\toffers: \n");

		for (Offer offer : this.offers) {
			sb.append(offer);
		}

		sb.append("\n}");

		return sb.toString();
	}
}
