package naivebayes;

public class NaiveBayesResult {

	private Classe classe;
	private double positivo;
	private double negativo;
	
	public NaiveBayesResult() {
		
	}
	public NaiveBayesResult(Classe classe, double positivo, double negativo) {
		this.classe = classe;
		this.positivo = positivo;
		this.negativo = negativo;
	}
	public Classe getClasse() {
		return classe;
	}
	public void setClasse(Classe classe) {
		this.classe = classe;
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
	@Override
	public String toString() {
		return "NaiveBayesResult [classe=" + classe + ", positivo=" + positivo + ", negativo=" + negativo + "]";
	}
	
}
