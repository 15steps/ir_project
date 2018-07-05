package project2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import project2.model.Posting;
import project2.model.Token;

public class Postings implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Posting> postings = new ArrayList<Posting>();
	private Map<String, Posting> map = new HashMap<String, Posting>();
	private boolean grap;
	private int qtd;

	public Postings(){
		
	}
	public Postings(boolean grap, Map<String, List<Token>> map) {
		this.grap = grap;

		for (String key : map.keySet()) {

			List<Token> value = map.get(key);
			int[] docIDs = new int[value.size()];
			int[] qtd = new int[value.size()];
			int[] size = new int[value.size()];
			String[] docName = new String[value.size()];

			for (int i = 0; i < value.size(); i++) {
				Token t = value.get(i);
				docIDs[i] = t.getIdDocument();
				qtd[i] = t.getCount();
				docName[i] = t.getNameDocument();
				size[i] = t.getSize();
			}

			if (this.grap) {
				docIDs = this.idToGrap(docIDs);
			}

			Posting p = new Posting(key, docIDs, qtd, this.grap, docName);
			p.setSize(size);
			this.postings.add(p);
		}
		
		for (Posting p : this.postings) {
			this.map.put(p.getTerm(), p);
		}
		
	}

	public Posting consult(String term) {
		if(this.map == null){
			this.map = new HashMap<String, Posting>();
			for (Posting p : this.postings) {
				this.map.put(p.getTerm(), p);
			}
		}
		return this.map.containsKey(term) ? this.map.get(term) : null;
	}

	public int[] idToGrap(int[] IDs) {

		int[] graps = new int[IDs.length];
		for (int i = 0; i < IDs.length; i++) {
			if (i == 0) {
				graps[i] = IDs[i];
			} else {
				graps[i] = IDs[i] - IDs[i - 1];
			}
		}
		return graps;
	}

	public int[] grapToIds(int[] grap) {

		int[] IDs = new int[grap.length];
		for (int i = 0; i < IDs.length; i++) {
			if (i == 0) {
				IDs[i] = grap[i];
			} else {
				IDs[i] = IDs[i - 1] + grap[i];
			}
		}
		return IDs;
	}

	public void setMap(Map<String, Posting> map) {
		this.map = map;
	}

	public Map<String, Posting> getMap() {
		return map;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public List<Posting> getPostings() {
		return postings;
	}
	public void setPostings(List<Posting> postings) {
		this.postings = postings;
	}
	public boolean isGrap() {
		return grap;
	}
	public void setGrap(boolean grap) {
		this.grap = grap;
	}
	public List<Posting> getFrequence(int limit){
		List<Posting> list = new ArrayList<>();
		list.addAll(this.postings);
		Collections.sort(list);
		return list = list.stream().limit(limit).collect(Collectors.toList());
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.grap);
		sb.append("\n");
		for(Posting p : this.postings){
			sb.append(p.toPrint());
			sb.append("\n");
		}
		return sb.toString();
	}

}
