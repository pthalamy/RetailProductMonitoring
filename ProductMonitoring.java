
// import org.json.*;
import java.util.*;
import java.io.*;

public class ProductMonitoring {

    HashMap<String, Double> productASINList = new HashMap<String, Double>();

	public ProductMonitoring() {
		super();
	}
	
	// TODO: Support other websites than amazon.it
	public static void main(String[] args) {
		ProductMonitoring pm = new ProductMonitoring();
		ArrayList<Product> products = new ArrayList<Product>();
		pm.initializeProductASINList();
		
		for (String asin : pm.productASINList.keySet()) {
			Parser parser = new Parser(asin, Country.ITALY);
			Product p = parser.parseProduct();
			p.setRetailPrice(pm.productASINList.get(p.ASIN));
			products.add(p);
		}

		// printAllProducts(products);
		
		if (performCrossSellingCheck(products))
			System.out.println("\nNo cross-selling detected.");
		else
			System.out.println("\nCross-selling detected, check above.");
	}

	// Return true if a cross selling offer was detected, false otherwise
	public static boolean performCrossSellingCheck(ArrayList<Product> products) {
		boolean CSDetected = false;
			
		// Check cross-selling for each product
		for (Product p : products) {
			for (Offer o : p.offers) {
				if (!p.country.equals(o.country)) {
				    CSDetected = true;
					reportCS(p, o);
				}					
			}
		}

		return CSDetected;
	}

	public static void reportCS(Product p, Offer o) {
		System.out.println("CS Report:");
		p.printCSDescription();
		o.printCSDescription();
		System.out.println("");
	}

	public static void printAllProducts(ArrayList<Product> products) {
		for (Product p : products) {
			System.out.println(p);
		}
	}

	public void initializeProductASINList() {
		// Bowers & Wilkins
		this.productASINList.put("B00BK7PNIG", 191.0);
		this.productASINList.put("B00NOOWVCE", 254.0);
		this.productASINList.put("B00Y0Q9LFU", 339.0);
		this.productASINList.put("B016JZZBPS", 594.0);
		this.productASINList.put("B00OLE9606", 296.0);
		this.productASINList.put("B003II3BM0", 424.0);

	}	
}
