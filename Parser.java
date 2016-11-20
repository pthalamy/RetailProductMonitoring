
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.net.URL;
import java.io.*;

public class Parser {

	private final String asin;
	private String url;
	private Country store;
	
	public Parser (String asin, Country store) {
		this.asin = asin;
		this.store = store;
		this.url = getProductPageFromASIN(asin);
	}

    public Product parseProduct() throws Exception {
		Product p = null;
		
		try {
			// Retrieve product offers page
			Document doc = Jsoup.connect(this.url)
				.timeout(30000)
				.userAgent(RandomUserAgent.getRandomUserAgent())
				.get();

			// Get missing product information and initialize product
			Elements h1 = doc.getElementsByTag("h1");
			if (h1.isEmpty()) {
				System.err.println("error: parsing is currently blocked by the Amazon Servers, please change your ip address."
								   + " Or product does not exist at url: " + url);
				return null;
			}
			
			String productName = h1.first().text();

			p = new Product(productName, asin, url, store);

			// Parse and instantiate offers
			Elements offers = doc.getElementsByClass("olpOffer");
			if (offers.isEmpty()) {
				// No offer for product
				return null;
			}
				
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
			companyName = "Amazon." + this.store.getExtension();
			expeditionCountry = this.store;
		} else {
			// Find expedition information among offer information
			Elements offerInfos = offer.getElementsByClass("a-list-item");
			String expFromString = "";
						
			for (Element item : offerInfos) {
				String text = item.text();
				if (text.contains("Spedizione da")
					|| text.contains("Expédié depuis")
					|| text.contains("Versand aus")
					|| text.contains("Dispatched from")) {
					expFromString = text;
					break;
				}
			}

			if (expFromString.equals("")) {
				Elements fulfilledByAmazon = offer.getElementsByClass("a-popover-trigger a-declarative olpFbaPopoverTrigger");
				if (fulfilledByAmazon.isEmpty()) {
					System.err.println("error: Missing dispatch information!" + " url: " + url);
					expeditionCountry  = Country.OTHER;
				} else {
					// Product is shipped by Amazon
					expeditionCountry  = this.store;
				}
			} else {
				expeditionCountry = Country.expFromStringToCountry(expFromString, store);
			}
		}

		Elements prices = offer.getElementsByClass("olpOfferPrice");
		String priceStr = prices.first().text();

		return new Offer(companyName, companyUrl, expeditionCountry, priceStr);
	}
	
	public String getProductPageFromASIN(String asin) {
		return "https://www.amazon."
			+ this.store.getExtension()
			+ "/gp/offer-listing/"
			+ asin
			+ "/ref=dp_olp_new?ie=UTF8&condition=new";
	}
}
