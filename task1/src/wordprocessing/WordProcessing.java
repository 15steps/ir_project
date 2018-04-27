package wordprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordProcessing {

	public Map<String, Integer> tokensFrequency(String texto, Stopword stopword, boolean acentuacao, boolean pontuacao, boolean numeros){
		
		List<String> tokensList = this.tokens(texto, stopword, acentuacao, pontuacao, numeros);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String token : tokensList){
			Integer value = map.get(token);
			value = (value == null) ? 1 : (value+1);
			map.put(token, value);
		}
		
		return map;
	}
	
	public List<String> tokens(String texto, Stopword stopword, boolean acentuacao, boolean pontuacao, boolean numeros){
		
		List<String> tokensList = new ArrayList<String>();
		String wordPro = this.wordProcessing(texto, acentuacao, pontuacao, numeros);
		String[] tokensArray = wordPro.split("\\s+");
		
		for(String token : tokensArray){
			token = removerStopwords(token, stopword);
			if(!token.isEmpty()){
				tokensList.add(token);
			}
		}
		
		return tokensList;
	}
	
	public String wordProcessing(String texto, boolean acentuacao, boolean pontuacao, boolean numeros){
		
		texto = texto.toLowerCase();
		if(acentuacao){
			texto = this.removerAcentos(texto);
		}
		if(pontuacao){
			texto = this.removerPontuacao(texto);
		}
		if(numeros){
			texto = this.removerNumeros(texto);
		}
		
		return texto.replaceAll("\\s+", " ").trim();
	}
	
	private String removerStopwords(String token, Stopword stopword){
		return stopword.getStopwordSet().contains(token) ? "" : token;
	}
	
	private String removerNumeros(String palavra){
		return palavra.replaceAll("[0-9]","");
	}

	private String removerPontuacao(String palavra){
		return palavra.replaceAll("[^a-zA-Z0-9]", " ");
	}

	private String removerAcentos(String palavra) {    
		palavra = palavra.replaceAll("[áàãâ]","a");
		palavra = palavra.replaceAll("[éèê]","e");
		palavra = palavra.replaceAll("[íìî]","i");
		palavra = palavra.replaceAll("[óõõô]","o");
		palavra = palavra.replaceAll("[úùû]","u");
		palavra = palavra.replaceAll("ç","c");
		return palavra;
	}

}
