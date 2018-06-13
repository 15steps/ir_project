package project2.model;

import java.io.Serializable;

public class Posting implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String term;
	private int[] docIDs;
	private int[] graps;
	private int[] qtd;
	private boolean grap;
	private String[] docName;

	public Posting() {
		
	}
	public Posting(String term, int[] docIDs, int[] graps, int[] qtd, boolean grap, String[] docName) {
		this.term = term;
		this.docIDs = docIDs;
		this.graps = graps;
		this.qtd = qtd;
		this.grap = grap;
		this.docName = docName;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int[] getDocIDs() {
		return docIDs;
	}
	public void setDocIDs(int[] docIDs) {
		this.docIDs = docIDs;
	}
	public int[] getGraps() {
		return graps;
	}
	public void setGraps(int[] graps) {
		this.graps = graps;
	}
	public int[] getQtd() {
		return qtd;
	}
	public void setQtd(int[] qtd) {
		this.qtd = qtd;
	}
	public boolean isGrap() {
		return grap;
	}
	public void setGrap(boolean grap) {
		this.grap = grap;
	}
	public String[] getDocName() {
		return docName;
	}
	public void setDocName(String[] docName) {
		this.docName = docName;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.term);
		sb.append(": ");

		for(int i=0; i<this.docIDs.length; i++){
			if(i>0){
				sb.append("; ");
			}

			if(this.grap){
				sb.append(this.graps[i]);
			}else{
				sb.append(this.docIDs[i]);
			}
			sb.append(',');
			sb.append(this.qtd[i]);
			sb.append(',');
			sb.append(this.docName[i]);
		}
		return sb.toString();
	}
}

