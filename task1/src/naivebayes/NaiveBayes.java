package naivebayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes {

	private Map<String, Palavra> mapa;
	private double total;
	private double positivo;
	private double negativo;

	public NaiveBayes() {
		this.mapa = new HashMap<String, Palavra>();
		this.total = 0;
		this.positivo = 0;
		this.negativo = 0;
	}
	
	public void train(String[] words, Classe classe){
		
		List<String> wordsList = new ArrayList<String>();
		for(String word : words){
			wordsList.add(word);
		}
		
		this.train(wordsList, classe);
	}
	
	public void train(List<String> words, Classe classe){
		
		Map<String, Integer> map = this.frequence(words);
	
		for(String word : map.keySet()){
			
			Palavra p = this.mapa.get(word);
			if(p == null){
				p = new Palavra(word);
			}
			
			int value = map.get(word);
			
			if(classe.equals(Classe.POSITIVO)){
				p.sumPositivo(value);
				this.positivo += value;
			}else if(classe.equals(Classe.NEGATIVO)){
				p.sumNegativo(value);
				this.negativo += value;
			}
			
			this.mapa.put(word, p);
			this.total += value;
		}
	}
	
	private double prob(String palavra, Classe classe){
		
		double val = 0;
		double qtd = 0;
		
		if(classe.equals(Classe.POSITIVO)){
			val = positivo;
			qtd = mapa.get(palavra).getPositivo();
		}else if(classe.equals(Classe.NEGATIVO)){
			val = negativo;
			qtd = mapa.get(palavra).getNegativo();
		}
		
		double palavra_classe = qtd / val;
		double classe_total = val / this.total;
		double total = qtdPalavra(palavra) / this.total;
		
		if (total == 0 || val == 0){
			return total;
		}
		
		return (palavra_classe * classe_total) / total;
	}
	
	private Map<String, Integer> frequence(List<String> words){
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(String word : words){
			Integer value = map.get(word);
			value = (value == null) ? 1 : (value+1);
			map.put(word, value);
		}
		
		return map;
	}

	public NaiveBayesResult classify(List<String> words){
		
		Map<String, Integer> map = this.frequence(words);
		double positivo = 0;
		double negativo = 0;
		
		for(String palavra : map.keySet()){
			if(this.mapa.containsKey(palavra)){
				int value = map.get(palavra);
				double pos = prob(palavra, Classe.POSITIVO);
				double neg = prob(palavra, Classe.NEGATIVO);
				positivo += pos * value;
				negativo += neg * value;
			}
		}
		
		Classe classe;
		if(positivo > negativo){
			classe = Classe.POSITIVO;
		}else if (negativo > positivo){
			classe = Classe.NEGATIVO;
		}else{
			classe = Classe.NEUTRO;
		}
		
		return new NaiveBayesResult(classe, positivo, negativo);
	}
	
	public NaiveBayesResult classify(String ... words){
		
		List<String> wordsList = new ArrayList<String>();
		for(String word : words){
			wordsList.add(word);
		}
		
		return this.classify(wordsList);
	}
	
	public void clear(){
		this.mapa.clear();
		this.total = 0;
		this.positivo = 0;
		this.negativo = 0;
	}

	private double qtdPalavra(String palavra){
		return mapa.get(palavra).soma();
	}
	
}


