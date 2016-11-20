
public class Offer {
	public String companyName;
	public Country country;
	public String companyUrl;
	public Double price;

	public Offer(String companyName, String companyUrl, Country country, String priceStr) {
		super();

		this.companyName = companyName;
		this.country = country;
		this.companyUrl = companyUrl;
		this.price = offerPriceFromString(priceStr);
	}

	//! Ensure it works for all cases
	//! "EUR 1.234,45" -> 1234.45
	public static Double offerPriceFromString(String priceStr) {
		String wellFormattedPrice = "";
		int priceStartIndex;
		
		if (priceStr.contains("EUR")) {
			priceStartIndex = priceStr.indexOf("EUR ") + "EUR ".length();
			wellFormattedPrice = priceStr.substring(priceStartIndex).replace(".", "").replace(",", ".");
		} else if (priceStr.contains("£")) {
		    priceStartIndex = priceStr.indexOf("£") + "£".length();
			wellFormattedPrice = priceStr.substring(priceStartIndex).replace(".", "").replace(",", ".");
		} else {
			System.err.println("error: offerPriceFromString: unsupported input price string " + priceStr);
			System.exit(1);
		}
		
		return Double.valueOf(wellFormattedPrice);
	}

	public void printCSDescription() {
		System.out.println("\tOffer: " + companyName + "; From: " + country + "; price: " + price);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\tOffer\t{\n");
		sb.append("\t\tname: " + companyName + "\n");
		sb.append("\t\turl: " + companyUrl + "\n");
		sb.append("\t\tcountry: " + country + "\n");
		sb.append("\t\tprice: " + price + "\n");
		sb.append("\t}");
		
		return sb.toString();
	}

}
