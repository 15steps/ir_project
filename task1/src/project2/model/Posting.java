package project2.model;

import java.io.Serializable;
import java.util.Arrays;

public class Posting implements Serializable {

	private static final long serialVersionUID = 1L;

	private String term;
	private int[] docIDs;
	private int[] qtd;
	private int[] size;
	private boolean grap;
	private String[] docName;
	// Frequencia do termo nos documentos
	private int freq;
	// Frequencia de documentos com o termo (tamanho do posting)
	private int df;

	public Posting() {

	}

	public Posting(String term, int[] docIDs, int[] qtd, boolean grap, String[] docName) {
		this.term = term;
		this.docIDs = docIDs;
		this.qtd = qtd;
		this.grap = grap;
		this.docName = docName;
		this.freq = Arrays.stream(qtd).sum();
		this.df = docIDs.length;
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

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getDf() {
		return df;
	}

	public void setDf(int df) {
		this.df = df;
	}

	public int[] getSize() {
		return size;
	}

	public void setSize(int[] size) {
		this.size = size;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.term);
		sb.append(": ");

		for (int i = 0; i < this.docIDs.length; i++) {
			if (i > 0) {
				sb.append("; ");
			}
			sb.append(this.docIDs[i]);
			sb.append(',');
			if(this.qtd != null){
				sb.append(this.qtd[i]);
			}else{
				sb.append(0);
			}
			sb.append(',');
			sb.append(this.docName[i]);
		}
		return sb.toString();
	}
}
