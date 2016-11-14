
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

	// #TODO
	public static Country expFromStringToCountry(String expFrom) {
		return OTHER;
	}
}
