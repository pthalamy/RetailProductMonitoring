
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
	public static Double offerPriceFromString(String priceStr) {
		int priceStartIndex = priceStr.indexOf("EUR ") + "EUR ".length();
		String wellFormattedPrice = priceStr.substring(priceStartIndex).replace(",", ".");
		
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
