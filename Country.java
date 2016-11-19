
public enum Country {
	ITALY,
	FRANCE,
	GERMANY,
	UK,
	OTHER;

	@Override
	public String toString() {
		switch(this) {
		case ITALY: return "Italy"; 
		case FRANCE: return "France"; 
		case GERMANY: return "Germany"; 
		case UK: return "UK"; 
		default: return "Other"; 
		}
	}

	public static Country deduceFromUrl(String url) {
		int domainIndex = url.indexOf("amazon.");
		if (domainIndex == -1) {
			System.err.println("error: deduceFromUrl: invalid url: " + url);
			return OTHER; // Should throw exception
		}

		int nextSlash = url.indexOf('/', domainIndex);
		if (domainIndex == -1) {
			System.err.println("error: deduceFromUrl: invalid url: " + url);
			return OTHER; // Should throw exception
		}

		// System.err.println(url);
		// System.err.println(domainIndex + " " + "amazon.".length() + " " + nextSlash);
		String countryExt = url.substring(domainIndex + "amazon.".length(), nextSlash);
		// System.err.println(countryExt);

		switch(countryExt) {
		case "it": return ITALY;
		case "fr": return FRANCE;
		case "de": return GERMANY;
		case "co.uk": return UK;
		default: return OTHER;
		}
	}

	// Ensure it works for all cases
	//  Input format: "*: <Country>."
	public static Country expFromStringToCountry(String expFrom) {
		//  Position here  "*: <Country>."
		//                     ^
		//                     |
		int countryIndex = expFrom.lastIndexOf(":") + ": ".length();

		// Trim to <Country>
		String countryString = expFrom.substring(countryIndex, expFrom.length() - 1).trim();		
		
		switch(countryString) {
		case "Italia": return ITALY;
		case "France": return FRANCE;
		case "Germany": return GERMANY;
		case "United Kingdom": return UK;
		default:
			System.err.println("warning: unknown country: " + countryString);
			return OTHER;
		}
	}

	// Returns domain extension corresponding to this instance of Country
	public String getExtension() {
		switch(this) {
		case ITALY: return "it";
		case FRANCE: return "fr";
		case GERMANY: return "de";
		case UK: return "co.uk";
		default:
			System.err.println("error: Country.getExtension(): unknown country.");
			return "ERROR";
		}
	}

}
