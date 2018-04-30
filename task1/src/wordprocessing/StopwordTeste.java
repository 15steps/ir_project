package wordprocessing;

import java.util.Map;

public class StopwordTeste {

	public static void main(String[] args) {
		
		WordProcessing pro = new WordProcessing();
		String palavra = "https://www.google.com.br/search?q=la+casa+de+papel&oq=la+casa&aqs=chrome.0.69i59j69i57.1733j0j7&sourceid=chrome&ie=UTF-8";
		
		Stopword stopword = Stopword.PORTUGUESE;
		
		System.out.println(pro.wordProcessing(palavra, stopword) + "\n");
		
		Map<String, Integer> map = pro.tokensFrequency(palavra, stopword);
		for(String key : map.keySet()){
			System.out.println(key + "\t" + map.get(key));
		}
		
	}

}
