package wordprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordProcessing {

	public WordProcessing() {
		
	}

	public Map<String, Integer> tokensFrequency(String texto, Stopword stopword, boolean acentuacao, boolean pontuacao){
		
		List<String> tokensList = this.tokens(texto, stopword, acentuacao, pontuacao);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String token : tokensList){
			Integer value = map.get(token);
			value = (value == null) ? 1 : (value+1);
			map.put(token, value);
		}
		
		return map;
	}
	
	public List<String> tokens(String texto, Stopword stopword, boolean acentuacao, boolean pontuacao){
		
		List<String> tokensList = new ArrayList<String>();
		String wordPro = this.wordProcessing(texto, acentuacao, pontuacao);
		String[] tokensArray = wordPro.split("\\s+");
		
		for(String token : tokensArray){
			token = removerStopwords(token, stopword);
			if(!token.isEmpty()){
				tokensList.add(token);
			}
		}
		
		return tokensList;
	}
	
	public String wordProcessing(String texto, boolean acentuacao, boolean pontuacao){
		
		texto = texto.toLowerCase();
		if(acentuacao){
			texto = this.removerAcentos(texto);
		}
		if(pontuacao){
			texto = this.removerPontuacao(texto);
		}
		
		return texto;
	}
	
	public String removerStopwords(String token, Stopword stopword){
		return stopword.getStopwordSet().contains(token) ? "" : token;
	}

	public String removerAcentos(String palavra) {    
		palavra = palavra.replaceAll("[aáàãâ]","a");
		palavra = palavra.replaceAll("[eéèê]","e");
		palavra = palavra.replaceAll("[iíìî]","i");
		palavra = palavra.replaceAll("[oóõõô]","o");
		palavra = palavra.replaceAll("[uúùû]","u");
		palavra = palavra.replaceAll("ç","c");
		return palavra;  
	}

	public String removerPontuacao(String palavra){
		return palavra.replaceAll("[^a-zA-Z0-9]", " ");
	}

}
