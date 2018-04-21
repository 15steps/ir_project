package wordprocessing;

import java.util.Map;

public class StopwordTeste {

	public static void main(String[] args) {
		
		WordProcessing pro = new WordProcessing();
		String palavra = "de o que sssssss do da em um para e com do da teste com nao não, é teste ..teste ó | ] } ";
		
		Stopword stopword = Stopword.NONE;
		
		System.out.println(pro.wordProcessing(palavra, true, true) + "\n");
		
		Map<String, Integer> map = pro.tokensFrequency(palavra, stopword, true, true);
		for(String key : map.keySet()){
			System.out.println(key + "\t" + map.get(key));
		}
		
	}

}
