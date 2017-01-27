

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
	public static Country expFromStringToCountry(String expFrom, Country store) {
		int countryStartIndex = 0;
		int countryEndIndex = 0;
		
		switch(store) {
		case ITALY: ;
			//  Position here  "Spedizione da: <Country>."
			//                     ^
			//                     |
			countryStartIndex = expFrom.lastIndexOf(":") + ": ".length();
			countryEndIndex = expFrom.length() - 1;
			break;
		case FRANCE:
			//  Position here  "Expédié depuis <Country>."
			//                                 ^
			//                                 |
			countryStartIndex = expFrom.lastIndexOf("Expédié depuis ") + "Expédié depuis ".length();
			countryEndIndex = expFrom.length() - 1;
			break;
		case GERMANY: 
			//  Position here  "Versand aus <Country>."
			//                              ^
			//                              |
			countryStartIndex = expFrom.lastIndexOf("Versand aus ") + "Versand aus ".length();
			countryEndIndex = expFrom.length();
			break;
		case UK: 
			//  Position here  "Dispatched from <Country>."
			//                              ^
			//                              |
			countryStartIndex = expFrom.lastIndexOf("Dispatched from ") + "Dispatched from ".length();
			countryEndIndex = expFrom.length() - 1;
			break;
		default:
			System.err.println("error: expFromStringToCountry: unknown store: " + store);
			System.exit(1);
		}


		// Trim to <Country>
		String countryString = expFrom.substring(countryStartIndex, countryEndIndex).trim();		
		// System.out.println(expFrom + " -> " + countryString);
		
		switch(countryString) {
		case "Italia":
		case "Italie":
		case "Italy":
		case "Italien":
			return ITALY;
		case "France":
		case "Francia":
			return FRANCE;
		case "Germany":
		case "Deutschland":
		case "Allemagne":
		case "Germania":
			return GERMANY;
		case "United Kingdom":
		case "Vereinigtes Königreich":
		case "Regno Unito":
			return UK;
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
