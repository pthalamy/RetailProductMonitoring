
// import org.json.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class ProductMonitoring {

    HashMap<String, Double> productASINList = new HashMap<String, Double>();	
	
	public ProductMonitoring() {
		super();
	}
	
	public static void main(String[] args) {
		ProductMonitoring pm = new ProductMonitoring();
		pm.initializeProductASINList();

		for (Country country : Country.values()) {
			if (!country.equals(Country.OTHER)) {
				System.out.println("\n=> Parsing data for country: " + country);

				ArrayList<Product> products = new ArrayList<Product>();
				for (String asin : pm.productASINList.keySet()) {
					try {
						// Wait 1/10th of a second to avoid Amazon Ban
					    TimeUnit.MILLISECONDS.sleep(500);
						
						Parser parser = new Parser(asin, country);
						Product p = parser.parseProduct();
						if (p != null) {
							p.setRetailPrice(pm.productASINList.get(p.ASIN));
							products.add(p);
						}
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

		// Denon
		this.productASINList.put("B007Y3OBOC", 194.0);
		this.productASINList.put("B007Y3OBUG", 194.0);
		this.productASINList.put("B007Y3OC0K", 296.0);
		this.productASINList.put("B007Y3OC50", 296.0);
		this.productASINList.put("B00ENI4QJG", 1189.0);
		this.productASINList.put("B00ENI4QTQ", 1189.0);
		this.productASINList.put("B007Y3OBAQ", 211.0);
		this.productASINList.put("B007Y3OBF6", 211.0);
		this.productASINList.put("B007Y3OBIS", 296.0);
		this.productASINList.put("B007Y3OBKQ", 296.0);
		this.productASINList.put("B00EUV4YRK", 1189.0);
		this.productASINList.put("B00EUV4YJI", 1189.0);
		this.productASINList.put("B00EUV4YJI", 1189.0);
		this.productASINList.put("B01GQ85GW6", 509.0);
		this.productASINList.put("B01GQ85GLW", 636.0);
		this.productASINList.put("B01GQ85GIU", 934.0);
		this.productASINList.put("B00ZGYE8DA", 1359.0);
		this.productASINList.put("B00ZGYE9UW", 1359.0);
		this.productASINList.put("B00N3340ZM", 1869.0);
		this.productASINList.put("B017K5OTQS", 2124.0);
		this.productASINList.put("B017XGC0YW", 2124.0);
		this.productASINList.put("B00ZGYE4ZC", 2541.0);
		this.productASINList.put("B010M8SG7S", 2541.0);

		// PRO-JECT		
		this.productASINList.put("B00EL6RHQO", 186.0);
		this.productASINList.put("B00FZ6B9Z4", 228.0);
		this.productASINList.put("B00XCTJBVK", 211.0);
		this.productASINList.put("B00XHMI352", 211.0);
		this.productASINList.put("B01FXT5XEK", 254.0);
		this.productASINList.put("B00DKSUHO8", 233.0);
		this.productASINList.put("B00DKSUGOO", 233.0);
		this.productASINList.put("B00DKSUH52", 233.0);
		this.productASINList.put("B00DKSUGWQ", 296.0);
		this.productASINList.put("B00DKSUI42", 296.0);
		this.productASINList.put("B00DKSUHHK", 296.0);
		this.productASINList.put("B013LRJEFK", 339.0);
		this.productASINList.put("B013LRQFPC", 339.0);
		this.productASINList.put("B00HG07942", 267.0);
		this.productASINList.put("B00HG078IO", 301.0);
		this.productASINList.put("B00IIMXATU", 339.0);
		this.productASINList.put("B00IIMXA4U", 339.0);
		this.productASINList.put("B0080D9WJY", 339.0);
		this.productASINList.put("B0087ORBN0", 339.0);
		this.productASINList.put("B00I5HZ438", 381.0);
		this.productASINList.put("B00I5HZ2QM", 381.0);
		this.productASINList.put("B00I5HZ3F2", 381.0);
		this.productASINList.put("B00HG07ENI", 381.0);
		this.productASINList.put("B00A8EZAU8", 381.0);
		this.productASINList.put("B00AWB6C22", 424.0);
		this.productASINList.put("B00AWB6BM8", 424.0);
	}	
}
