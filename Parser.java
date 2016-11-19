
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.net.URL;
import java.io.*;

public class Parser {

	private final String asin;
	private String url;
	private Country country;
	
	public Parser (String asin, Country country) {
		this.asin = asin;
		this.country = country;
		this.url = getProductPageFromASIN(asin);
	}

    public Product parseProduct() throws Exception {
		Product p = null;
		
		try {
			// Retrieve product offers page
			Document doc = Jsoup.connect(this.url).timeout(30000).userAgent("Safari/602.2.14").get();

			// Get missing product information and initialize product
			Elements h1 = doc.getElementsByTag("h1");
			if (h1.isEmpty()) {
				System.err.println("error: parsing is currently blocked by the Amazon Servers, please change your ip address");
				System.exit(1);
			}
			
			String productName = h1.first().text();

			p = new Product(productName, asin, url, country);

			// Parse and instantiate offers
			Elements offers = doc.getElementsByClass("olpOffer");
			for (Element offer : offers) {
				p.offers.add(parseOffer(offer));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return p;
	}

	public Offer parseOffer(Element offer) {
		Elements h3 = offer.getElementsByTag("h3");
		String companyName = h3.first().text();

		String companyUrl = "";
		Elements aTags = h3.first().getElementsByTag("a");
		if (!aTags.isEmpty()) {
			companyUrl = aTags.first().attr("href");
		}

		Country expeditionCountry;				
		if (companyName.equals("")) { // This must be amazon offer
			companyName = "Amazon." + this.country.getExtension();
			expeditionCountry = this.country;
		} else {
			// Find expedition information among offer information
			Elements offerInfos = offer.getElementsByClass("a-list-item");
			String expFromString = "";
						
			for (Element item : offerInfos) {
				String text = item.text();
				if (text.contains("Spedizione")) {
					expFromString = text;
					break;
				}
			}

			if (expFromString.equals(""))
				System.err.println("error: Missing expedition information!");

			expeditionCountry = Country.expFromStringToCountry(expFromString);
		}

		Elements prices = offer.getElementsByClass("olpOfferPrice");
		String priceStr = prices.first().text();

		return new Offer(companyName, companyUrl, expeditionCountry, priceStr);
	}
	
	public String getProductPageFromASIN(String asin) {
		return "https://www.amazon."
			+ this.country.getExtension()
			+ "/gp/offer-listing/"
			+ asin
			+ "/ref=dp_olp_new?ie=UTF8&condition=new";
	}
}
