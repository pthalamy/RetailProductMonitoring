
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
		pm.initializeProductASINList();

		for (Country country : Country.values()) {
			if (!country.equals(Country.OTHER)) {
				System.out.println("\n=> Parsing data for country: " + country);

				ArrayList<Product> products = new ArrayList<Product>();
				for (String asin : pm.productASINList.keySet()) {
					try {
						Parser parser = new Parser(asin, country);
						Product p = parser.parseProduct();
						p.setRetailPrice(pm.productASINList.get(p.ASIN));
						products.add(p);
					} catch (Exception e) {
						System.err.println("error during parsing of ASIN: " + asin);
						e.printStackTrace();					
					}
				}

				// printAllProducts(products);
		
				if (performCrossSellingCheck(products))
					System.out.println("Cross-selling detected, check above.");
				else
					System.out.println("NO cross-selling detected.");

				if (country.equals(Country.ITALY)) {
					if (performPriceCheck(products))
						System.out.println("Price anomalies detected, check above.");
					else
						System.out.println("NO price anomalies detected.");
				}

				System.out.println("\nEnd of report for country: " + country);
				System.out.println("=========================================\n");			
			}

			// FOR NOW, ONLY ITALY IS SUPPORTED
			break;
		}
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

	// Return true if a price inconsistency was detected, false otherwise
	public static boolean performPriceCheck(ArrayList<Product> products) {
		boolean priceInconsistencyDetected = false;
			
		// Check cross-selling for each product
		for (Product p : products) {
			for (Offer o : p.offers) {
				if (p.retailPrice > o.price) {
				    priceInconsistencyDetected = true;
					reportPriceAnomaly(p, o);
				}					
			}
		}

		return priceInconsistencyDetected;
	}

	public static void reportCS(Product p, Offer o) {
		System.out.println("Cross-Selling Report:");
		p.printCSDescription();
		o.printCSDescription();
		System.out.println("");
	}

	public static void reportPriceAnomaly(Product p, Offer o) {
		System.out.println("Price Anomaly Report:");
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
