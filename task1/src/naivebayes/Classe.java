package naivebayes;

public enum Classe {
	
	POSITIVO(1, "POSITIVO"),
	NEGATIVO(-1, "NEGATIVO"),
	NEUTRO(0, "NEUTRO");
	
	private int classe;
	private String descricao;
	
	private Classe(int classe, String descricao) {
		this.classe = classe;
		this.descricao = descricao;
	}
	
	public int getClasse() {
		return classe;
	}
	public void setClasse(int classe) {
		this.classe = classe;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
