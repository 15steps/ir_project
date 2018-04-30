package naivebayes;

public class Palavra {
	
	private String palavra;
	private double positivo;
	private double negativo;
	
	public Palavra(String palavra, double positivo, double negativo) {
		this.palavra = palavra;
		this.positivo = positivo;
		this.negativo = negativo;
	}
	
	public Palavra(String palavra) {
		this.palavra = palavra;
		this.positivo = 0;
		this.negativo = 0;
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public double getPositivo() {
		return positivo;
	}

	public void setPositivo(double positivo) {
		this.positivo = positivo;
	}

	public double getNegativo() {
		return negativo;
	}

	public void setNegativo(double negativo) {
		this.negativo = negativo;
	}
	
	public void incrementPositivo(){
		this.positivo++;
	}
	
	public void incrementNegativo(){
		this.negativo++;
	}
	
	public void sumPositivo(int value){
		this.positivo += value;
	}
	
	public void sumNegativo(int value){
		this.negativo += value;
	}
	
	public double soma(){
		return negativo + positivo;
	}

	@Override
	public String toString() {
		return "Palavra [palavra=" + palavra + ", positivo=" + positivo + ", negativo=" + negativo + "]";
	}
	
}
