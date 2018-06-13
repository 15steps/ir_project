package wordprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordProcessing {

	 private static WordProcessing instance = new WordProcessing();
	 
	 private WordProcessing() {
	 
	 }

	 public static WordProcessing getInstance() {
		 return instance;
	 }
	    
	/**
	 * Separa a entrada em um map de frequencia dos tokens
	 * @param texto
	 * @param stopword
	 * @param acentuacao
	 * @param pontuacao
	 * @param numeros
	 * @return
	 */
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
	
	/**
	 * Separa em tokens a entrada
	 * @param texto
	 * @param stopword
	 * @param acentuacao
	 * @param pontuacao
	 * @param numeros
	 * @return
	 */
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
	
	/**
	 * Separa em tokens a entrada
	 * @param texto
	 * @param stopword
	 * @param regex
	 * Regex para usar como um filtro, se der match remove o token
	 * @param acentuacao
	 * @param pontuacao
	 * @param numeros
	 * @return
	 */
	public List<String> tokens(String texto, Stopword stopword, List<String> regex, boolean acentuacao, boolean pontuacao, boolean numeros){
		
		List<String> tokensList = new ArrayList<String>();
		String wordPro = this.wordProcessing(texto, acentuacao, pontuacao, numeros);
		String[] tokensArray = wordPro.split("\\s+");
		
		for(String token : tokensArray){
			token = removerStopwords(token, stopword);
			if(!token.isEmpty() && !this.isMatch(token, regex)){
				tokensList.add(token);
			}
		}
		
		return tokensList;
	}
	
	private boolean isMatch(String word, List<String> regex){
		for(String r : regex){
			if(word.matches(r)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Faz um processamento na entrada
	 * @param texto
	 * @param stopword
	 * @param acentuacao
	 * @param pontuacao
	 * @param numeros
	 * @return
	 */
	public String wordProcessing(String texto, Stopword stopword, boolean acentuacao, boolean pontuacao, boolean numeros){
		List<String> tokensList = this.tokens(texto, stopword, acentuacao, pontuacao, numeros);
		StringBuilder sb = new StringBuilder();
		for(String token : tokensList){
			sb.append(token);
			sb.append(" ");
		}
		return sb.toString().trim();
	}
	
	public Map<String, Integer> tokensFrequency(String texto, Stopword stopword){	
		return this.tokensFrequency(texto, stopword, true, true, true);
	}
	
	public List<String> tokens(String texto, Stopword stopword){
		return this.tokens(texto, stopword, true, true, true);
	}
	
	public String wordProcessing(String texto, Stopword stopword){
		return this.wordProcessing(texto, stopword, true, true, true);
	}
	
	private String wordProcessing(String texto, boolean acentuacao, boolean pontuacao, boolean numeros){
		
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
