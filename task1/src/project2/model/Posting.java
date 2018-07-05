package project2.model;

import java.io.Serializable;
import java.util.Arrays;

import project2.Util;

public class Posting implements Serializable, Comparable<Posting> {

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
		return Arrays.stream(qtd).sum();
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getDf() {
		return docIDs.length;
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
	
	public String toPrint(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.term);
		sb.append(";");
		
		Util util = new Util();
		
		for (int i = 0; i < this.docIDs.length; i++) {
			if (i > 0) {
				sb.append(";");
			}
			sb.append(util.numToVbCode(this.docIDs[i]));
			sb.append(',');
			if(this.qtd != null){
				sb.append(util.numToVbCode(this.qtd[i]));
			}else{
				sb.append(util.numToVbCode(0));
			}
			sb.append(',');
			sb.append(util.numToVbCode(this.size[i]));
		}
		
		return sb.toString();
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

	@Override
	public int compareTo(Posting o) {
		if (this.docIDs.length > o.getDocIDs().length){
			return -1;
		}
		if (this.docIDs.length < o.getDocIDs().length){
			return 1;
		}
		return 0;
	}
}
