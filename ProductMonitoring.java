
// import org.json.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class ProductMonitoring {
	
	public static void main(String[] args) {
		String brandCSV = "./brands.csv";
		String productsCSV = "./products.csv";
		
		HashMap<String, Brand> brands = readBrandsFromCSVFile(brandCSV);
		HashMap<String, Product> products = readProductsFromCSVFile(productsCSV, brands);

		// for (Country country : Country.values()) {
		// 	if (!country.equals(Country.OTHER)) {
		// 		System.out.println("\n=> Parsing data for country: " + country);

		// 		ArrayList<Product> products = new ArrayList<Product>();
		// 		for (String asin : pm.productASINList.keySet()) {
		// 			try {
		// 				// Wait 1/10th of a second to avoid Amazon Ban
		// 			    TimeUnit.MILLISECONDS.sleep(500);
						
		// 				Parser parser = new Parser(asin, country);
		// 				Product p = parser.parseProduct();
		// 				if (p != null) {
		// 					p.setRetailPrice(pm.productASINList.get(p.ASIN));
		// 					products.add(p);
		// 				}
		// 			} catch (Exception e) {
		// 				System.err.println("error during parsing of ASIN: " + asin);
		// 				e.printStackTrace();					
		// 			}
		// 		}

		// 		// printAllProducts(products);
		
		// 		if (performCrossSellingCheck(products))
		// 			System.out.println("Cross-selling detected, check above.");
		// 		else
		// 			System.out.println("NO cross-selling detected.");

		// 		if (country.equals(Country.ITALY)) {
		// 			if (performPriceCheck(products))
		// 				System.out.println("Price anomalies detected, check above.");
		// 			else
		// 				System.out.println("NO price anomalies detected.");
		// 		}

		// 		System.out.println("\nEnd of report for country: " + country);
		// 		System.out.println("=========================================\n");			
		// 	}
		// }
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
				if (o.price < p.retailPrice - 5 ) {
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

	public static HashMap<String, Product> readProductsFromCSVFile(String csvFile,
																   HashMap<String, Brand> brands) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

		HashMap<String, Product> products = new HashMap<>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] product = line.split(cvsSplitBy);

				// PRODUCT FORMAT: [BRAND, PRODUCTNAME, PRICE, ASIN]
                System.out.println("Product " +
								   " [" + product[0] +
								   ", " + product[1] +
								   ", " + product[2] +
								   ", " + product[3]
								   + "]");
				// CREATE PROD
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

		return null;
	}

	public static HashMap<String, Brand> readBrandsFromCSVFile(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

		HashMap<String, Brand> brands = new HashMap<>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] brand = line.split(cvsSplitBy);

				// BRAND FORMAT: [BRANDNAME, AUTHORIZEDDISCOUNT]
                System.out.println("BRAND " +
								   " [" + brand[1] +
								   ", " + brand[2] 
								   + "]");
				// CREATE BRAND
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

		return null;
	}

}
