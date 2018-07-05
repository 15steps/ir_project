package project2;

import java.util.List;
import java.util.Map;

import project2.model.Page;
import project2.model.Posting;

public class Fachada {
	
	private boolean gap;
	private Postings posting;
	private Consulta consulta;
	
	public Fachada(boolean gap){
		this.gap = gap;
		
		Util util = new Util();
		String path = "xml_nome_id/";
		List<Page> pages = util.ler(path);
		
		Postings atributos = util.processarPagesAtributos(pages, gap);
		Postings corpo = util.processarPages(pages, gap);
		
		this.posting = util.merge(atributos, corpo);
		this.consulta = new Consulta(this.posting);
	}
	
	public Map<Integer, Double> consult(String query, boolean useIdf, int k){
		return this.consulta.cosineScore(query, true, 5);
	}
	
	public List<Posting> mostFrequent(int k){
		return this.posting.getFrequency(k);
	}

	public boolean isGap() {
		return gap;
	}

	public void setGap(boolean gap) {
		this.gap = gap;
	}

	public Postings getPosting() {
		return posting;
	}

	public void setPosting(Postings posting) {
		this.posting = posting;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	
}
